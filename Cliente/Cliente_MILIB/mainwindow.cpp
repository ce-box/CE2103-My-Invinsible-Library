#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "qdebug.h"
using namespace std;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow){
    ui->setupUi(this);

    imagenCargada = "";
    galeriaIngresada = "";
    sizeImagen = "";

    colorearWidget(ui->ideTextEdit, "#373753");
    colorearWidget(ui->appOutputTextEdit, "#373753");

    ui->mainFrame->setStyleSheet(".QFrame { background-color : #464646 } ");
    ui->AppOutputLabel->setStyleSheet("QLabel { color : white }");
    ui->appOutputTextEdit->setTextColor("#ff2121");

    connect(ui->subirImagenPushButton, SIGNAL (clicked()), this, SLOT (abrirExploradorArchivos()));
    connect(ui->visualizarImagenPushButton, SIGNAL (clicked()), this, SLOT (obtenerDatoTabla()));
    connect(ui->runPushButton, SIGNAL (clicked()), this, SLOT (obtenerInputIDE()));
    connect(ui->commitPushButton, SIGNAL (clicked()), this, SLOT (commit()));
    connect(ui->rollbackPushButton, SIGNAL (clicked()), this, SLOT (rollback()));

    ServerLibrary* server = ServerLibrary::getServer();
    server->setServer("/Main_Server_war_exploded/api/server", "192.168.0.21", "8081");
    server->START();
}

void MainWindow::colorearWidget(QWidget* widget, QString colorFondo){
    QPalette paleta = widget->palette();
    paleta.setColor(QPalette::Base, colorFondo);
    paleta.setColor(QPalette::Text, Qt::white);
    widget->setPalette(paleta);
}

void MainWindow::abrirExploradorArchivos(){
    QStringList direccionImagenes = QFileDialog::getOpenFileNames(this, tr("Abrir Imagen/Galería"),"/home",tr("Imágenes PNG (*.png)"));
    if(direccionImagenes.size() == 0) return;
    QString imgDireccion = direccionImagenes[0];

    QPixmap imagen;
    imagen.load(imgDireccion, "PNG");

    QFile archivo(imgDireccion);
    sizeImagen = QString::number(archivo.size()/(1000000.0));

    QByteArray byteArray;
    QBuffer buffer(&byteArray);
    imagen.save(&buffer, "PNG");

    QByteArray byteArray64 = byteArray.toBase64();
    string data = byteArray64.toStdString();
    galeriaIngresada = QInputDialog::getText(this, "Galería", "Ingrese el nombre de la galería a la que pertenece la imagen:");
    imagenCargada = data;


}

void MainWindow::obtenerDatoTabla(){
    int cantidadFilas = ui->metadataTable->rowCount();
    bool datoEncontrado = false;
    int numeroFilaSeleccionada;
    for(int i = 0; i < cantidadFilas && !datoEncontrado; i++){
        if(ui->metadataTable->item(i,0)->isSelected()){
            datoEncontrado = true;
            numeroFilaSeleccionada = i;
        }
    }
    if(!datoEncontrado) return;
    visualizarImagen(numeroFilaSeleccionada);
}

// Aquí se debe de hacer el cambio, debe recibir el ID como parámetro
void MainWindow::visualizarImagen(int numeroFila){

    vector<string> imgId;
    boost::split(imgId,IdSeleccionados,boost::is_any_of(","));
    string id = imgId[numeroFila];
    QString ID = QString::fromUtf8(id.c_str());

    // Ocupo que tome el id y lo pase por parámetro en -- selectImgJSON(QString Id)
    ServerLibrary* server = ServerLibrary::getServer();
    QString respuestaServidor = server->SELECT_IMG(JsonSerializer::selectImgJSON(ID));

    QJsonDocument jsonDoc = QJsonDocument::fromJson(respuestaServidor.toUtf8());
    QJsonObject jsonObject = jsonDoc.object();

    // Imagenes seleccionadas se debe accionar cuando se elige
    imagenesSeleccionadas = jsonObject.value("imgStack").toString().toStdString();

    vector<string> imagenes;
    boost::split(imagenes, imagenesSeleccionadas, boost::is_any_of(","));
    string data64 = imagenes[0]; // Este era mi error!! :v
    QString QTdata = QString::fromStdString(data64);
    QByteArray dataRecibida(QTdata.toUtf8());
    QPixmap imagen;
    imagen.loadFromData(QByteArray::fromBase64(dataRecibida));


    VentanaImagen ventImagen;
    ventImagen.setModal(true);
    ventImagen.setImagen(imagen);
    ventImagen.exec();
}

void MainWindow::obtenerInputIDE(){
    ui->appOutputTextEdit->setText("");
    string inputIDE = ui->ideTextEdit->toPlainText().toStdString();
    LectorSintaxis* lector = new LectorSintaxis(inputIDE);
    string instruccion = lector->manejarInputIDE();
    delete lector;
    vector<string> vectorInstruccion;
    boost::split(vectorInstruccion, instruccion, boost::is_any_of("-"));
    int numeroInstruccion = stoi(vectorInstruccion[0]);
    switch (numeroInstruccion) {
    case 1:
        instruccionInsert(vectorInstruccion);
        break;
    case 2:
        instruccionSelect(vectorInstruccion);
        break;
    case 3:
        instruccionDelete(vectorInstruccion);
        break;
    case 4:
        instruccionUpdate(vectorInstruccion);
        break;
    default:
        manejarError(numeroInstruccion-4);
        break;
    }
}

void MainWindow::instruccionInsert(vector<string> vectorInstruccion){
    if(imagenCargada == ""){
        return manejarError(13);
    }
    vector<string> vectorSlots;
    boost::split(vectorSlots, vectorInstruccion[1], boost::is_any_of(","));
    Lista<QString>* listaSlots = new Lista<QString>;

    vector<string> vectorValues;
    boost::split(vectorValues, vectorInstruccion[2], boost::is_any_of(","));
    Lista<QString>* listaValues = new Lista<QString>;

    for(int i = 0; i < vectorSlots.size(); i++){
        listaSlots->push_back(QString::fromStdString(vectorSlots[i]));
        listaValues->push_back(QString::fromStdString(vectorValues[i]));
    }
    listaSlots->push_back("gallery");
    listaValues->push_back(galeriaIngresada);
    listaSlots->push_back("size");
    listaValues->push_back(sizeImagen);

    QString json = JsonSerializer::insertJSON(listaSlots, listaValues, QString::fromStdString(imagenCargada));
    ServerLibrary* server = ServerLibrary::getServer();
    server->INSERT(json);

    imagenCargada = "";
    galeriaIngresada = "";
    sizeImagen = "";
}

void MainWindow::instruccionSelect(vector<string> vectorInstruccion){
    vector<string> vectorSlots;
    boost::split(vectorSlots, vectorInstruccion[1], boost::is_any_of(","));
    Lista<QString>* listaSlots = new Lista<QString>;
    if(vectorSlots[0] != "*"){
        for(int i = 0; i < vectorSlots.size(); i++)
            listaSlots->push_back(QString::fromStdString(vectorSlots[i]));
    }

    Lista<QString>* listaVarWhere = new Lista<QString>;
    Lista<QString>* listaValorWhereA = new Lista<QString>;
    Lista<QString>* listaValorWhereB = new Lista<QString>;
    
    if(vectorInstruccion.size() > 2){
        QString varWhere = QString::fromStdString(vectorInstruccion[2]);
        listaVarWhere->push_back(varWhere);

        vector<string> vectorValoresWhere;
        boost::split(vectorValoresWhere, vectorInstruccion[3], boost::is_any_of(","));
        QString valorWhereA = QString::fromStdString(vectorValoresWhere[0]);

        listaValorWhereA->push_back(valorWhereA);
        if(vectorValoresWhere.size() > 1){
            QString valorWhereB = QString::fromStdString(vectorValoresWhere[1]);
            listaValorWhereB->push_back(valorWhereB);
        }
    }
    QString json = JsonSerializer::selectJSON(listaSlots, listaVarWhere, listaValorWhereA, listaValorWhereB);
    ServerLibrary* server = ServerLibrary::getServer();
    QString respuestaServidor = server->SELECT(json);
    QJsonDocument jsonDoc = QJsonDocument::fromJson(respuestaServidor.toUtf8());
    QJsonObject jsonObject = jsonDoc.object();
    string status = jsonObject.value("status").toString().toStdString();
    //if(status != "Done")
    string datosSeleccionados = jsonObject.value("MetadataStack").toString().toStdString();
    // Imagenes seleccionadas se debe accionar cuando se elige
    //imagenesSeleccionadas = jsonObject.value("imgStack").toString().toStdString();

    // Este es el String que contendrá los Id seleccionados
    IdSeleccionados = jsonObject.value("IdStack").toString().toStdString();
    qDebug() << "Ids >>" << QString::fromStdString(IdSeleccionados);
    insertarEnTabla(datosSeleccionados);
}

void MainWindow::instruccionDelete(vector<string> vectorInstruccion){
    QString varWhere = QString::fromStdString(vectorInstruccion[1]);
    Lista<QString>* listaVarWhere = new Lista<QString>;
    listaVarWhere->push_back(varWhere);

    QString valorWhere = QString::fromStdString(vectorInstruccion[2]);
    Lista<QString>* listaValorWhere = new Lista<QString>;
    listaValorWhere->push_back(valorWhere);

    QString json = JsonSerializer::deleteJSON(listaVarWhere, listaValorWhere);
    ServerLibrary* server = ServerLibrary::getServer();
    server->DELETE(json);
}

void MainWindow::instruccionUpdate(vector<string> vectorInstruccion){
    vector<string> vectorSlots;
    boost::split(vectorSlots, vectorInstruccion[1], boost::is_any_of(","));
    Lista<QString>* listaSlots = new Lista<QString>;

    vector<string> vectorValues;
    boost::split(vectorValues, vectorInstruccion[2], boost::is_any_of(","));
    Lista<QString>* listaValues = new Lista<QString>;

    for(int i = 0; i < vectorSlots.size(); i++){
        listaSlots->push_back(QString::fromStdString(vectorSlots[i]));
        listaValues->push_back(QString::fromStdString(vectorValues[i]));
    }

    QString varWhere = QString::fromStdString(vectorInstruccion[3]);
    Lista<QString>* listaVarWhere = new Lista<QString>;
    listaVarWhere->push_back(varWhere);

    QString valorWhere = QString::fromStdString(vectorInstruccion[4]);
    Lista<QString>* listaValorWhere = new Lista<QString>;
    listaValorWhere->push_back(valorWhere);

    QString json = JsonSerializer::updateJSON(listaSlots, listaValues, listaVarWhere, listaValorWhere);
    ServerLibrary* server = ServerLibrary::getServer();
    server->UPDATE(json);
}

void MainWindow::manejarError(int numeroError){
    QString mensajeError = "Syntax Error: ";
    switch(numeroError){
    case 1:
        mensajeError += "SQL Instruction was not found.";
        break;
    case 2:
        mensajeError += "In INSERT Instruction\n\"INTO METADATA\" syntax instruction was not found.";
        break;
    case 3:
        mensajeError += "In INSERT Instruction\nColumn names were not found.";
        break;
    case 4:
        mensajeError += "In INSERT Instruction\nYou must close parenthesis.";
        break;
    case 5:
        mensajeError += "In INSERT Instruction\n\"VALUES\" syntax instruction was not found.";
        break;
    case 6:
        mensajeError += "Instruction termination was not found. Must close instruction with \";\".";
        break;
    case 7:
        mensajeError += "In SELECT Instruction\nColumn names were not found.";
        break;
    case 8:
        mensajeError += "\"FROM METADATA\" syntax instruction was not found.";
        break;
    case 9:
        mensajeError += "\"WHERE\" syntax instruction was not found.";
        break;
    case 10:
        mensajeError += "\"WHERE\" column name was not found.";
        break;
    case 11:
        mensajeError += "In DELETE Instruction\n\"FROM METADATA WHERE\" syntax instruction was not found.";
        break;
    case 12:
        mensajeError += "In UPDATE Instruction\n\"METADATA\" or \"SET\" syntax instructions were not found.";
        break;
    case 13:
        mensajeError = "ERROR: You must upload an image before INSERT instruction.\nPlease, click \"Upload Image\" "
                       "and select an image to continue.";
        break;
    case 14:
        mensajeError += "You must close string value.";
        break;
    default:
        mensajeError += "Undefined";
        break;
    }
    ui->appOutputTextEdit->setText(mensajeError);
}

void MainWindow::insertarEnTabla(string datosSeleccionados){
    vector<string> elementos;
    boost::split(elementos, datosSeleccionados, boost::is_any_of("-"));
    if(elementos.size() == 0) return;
    configurarNombreColumnas(elementos);
    elementos.erase(elementos.begin());
    insertarFilas(elementos);
}

void MainWindow::configurarNombreColumnas(vector<string> elementos){
    vector<string> headers;
    boost::split(headers, elementos[0], boost::is_any_of(","));
    int cantColumnas = headers.size();
    ui->metadataTable->setColumnCount(cantColumnas);
    QStringList nombreColumnas;
    int tamanioCol = 499/cantColumnas;
    for(int i = 0; i < cantColumnas; i++){
        nombreColumnas.append(QString::fromStdString(headers[i]));
        ui->metadataTable->horizontalHeader()->resizeSection(i, tamanioCol);
    }
    ui->metadataTable->setHorizontalHeaderLabels(nombreColumnas);
}

void MainWindow::insertarFilas(vector<string> elementos){
    int cantFilas = elementos.size();
    ui->metadataTable->setRowCount(cantFilas);
    vector<string> fila;
    for(int i = 0; i < cantFilas; i++){
        boost::split(fila, elementos[i], boost::is_any_of(","));
        for(int j = 0; j < fila.size(); j++)
            ui->metadataTable->setItem(i, j, new QTableWidgetItem(QString::fromStdString(fila[j])));
        fila.clear();
    }
}

void MainWindow::commit(){
    ServerLibrary* server = ServerLibrary::getServer();
    server->COMMIT();
}

void MainWindow::rollback(){
    ServerLibrary* server = ServerLibrary::getServer();
    server->BACK();
}


MainWindow::~MainWindow(){
    delete ui;
}

/*INSERT INTO METADATA(name,author,date,size)
VALUES("pikachuBailando.png", "Ash Ketchup", "2019", "2MB"); */

/*
SELECT name, author FROM METADATA
WHERE date = "2019"; //WHERE date BETWEEN 0 AND 5; */

//DELETE FROM METADATA WHERE date = "2019";

/*
UPDATE METADATA
SET date = "2020", author = "Brock"
WHERE name = "pikachuBailando.png";
*/
