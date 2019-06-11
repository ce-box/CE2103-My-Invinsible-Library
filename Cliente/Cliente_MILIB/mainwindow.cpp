#include "mainwindow.h"
#include "ui_mainwindow.h"
using namespace std;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow){
    ui->setupUi(this);

    imagenCargada = "";
    galeriaIngresada = "";

    colorearWidget(ui->ideTextEdit, "#373753");
    colorearWidget(ui->appOutputTextEdit, "#373753");

    ui->mainFrame->setStyleSheet(".QFrame { background-color : #464646 } ");
    ui->AppOutputLabel->setStyleSheet("QLabel { color : white }");

    connect(ui->subirImagenPushButton, SIGNAL (clicked()), this, SLOT (abrirExploradorArchivos()));
    connect(ui->runPushButton, SIGNAL (clicked()), this, SLOT (obtenerInputIDE()));
    connect(ui->commitPushButton, SIGNAL (clicked()), this, SLOT (commit()));
    connect(ui->rollbackPushButton, SIGNAL (clicked()), this, SLOT (rollback()));

    ServerLibrary* server = ServerLibrary::getServer();
    server->setMilib("/MILIB_Servidor_war_exploded/api/database", "192.168.100.20");
    server->setRaid("/MILIB_RAID_war_exploded/api/raid", "192.168.100.20");
    server->START();

    vector<string> ejmTabla;
    ejmTabla.push_back("NOMBRE,ARTISTA,DURACION,ALBUM");
    ejmTabla.push_back("Karma Police,Radiohead,4:27,OK Computer");
    ejmTabla.push_back("De Musica Ligera,Soda Estereo,4:27,De Musica Ligera");
    insertarEnTabla(ejmTabla);
}

void MainWindow::colorearWidget(QWidget* widget, QString colorFondo){
    QPalette paleta = widget->palette();
    paleta.setColor(QPalette::Base, colorFondo);
    paleta.setColor(QPalette::Text, Qt::white);
    widget->setPalette(paleta);
}

void MainWindow::abrirExploradorArchivos(){
    //TODO: Decidir si solo insertar imagenes una por una o seleccionar varias imagenes.
    QStringList direccionImagenes = QFileDialog::getOpenFileNames(this, tr("Abrir Imagen/Galería"),"/home",tr("Imágenes PNG (*.png)"));
    QString imgDireccion = direccionImagenes[0];

    QPixmap imagen;
    imagen.load(imgDireccion, "PNG");

    QFile archivo(imgDireccion);
    sizeImagen = QString::number(archivo.size()/(1000000.0));
    qDebug()<<sizeImagen;

    QByteArray byteArray;
    QBuffer buffer(&byteArray);
    imagen.save(&buffer, "PNG");

    QByteArray byteArray64 = byteArray.toBase64();
    string data = byteArray64.toStdString();
    imagenCargada = data;

    galeriaIngresada = QInputDialog::getText(this, "Galería", "Ingrese el nombre de la galería a la que pertenece la imagen:");

    //visualizarImagen(data);
}

void MainWindow::visualizarImagen(string data64){
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
    string inputIDE = ui->ideTextEdit->toPlainText().toStdString();
    LectorSintaxis* lector = new LectorSintaxis(inputIDE);
    string instruccion = lector->manejarInputIDE();
    qDebug()<<instruccion.c_str();
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
        qDebug()<<"DEBE CARGAR UNA IMAGEN ANTES DE ENVIAR INSTRUCCION INSERT.";
        return;
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
    listaSlots->push_back("galeria");
    listaValues->push_back(galeriaIngresada);
    listaSlots->push_back("size");
    listaValues->push_back(sizeImagen);

    QString json = JsonSerializer::insertJSON(listaSlots, listaValues);
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
    server->SELECT(json);
}

void MainWindow::instruccionDelete(vector<string> vectorInstruccion){
    QString varWhere = QString::fromStdString(vectorInstruccion[2]);
    Lista<QString>* listaVarWhere = new Lista<QString>;
    listaVarWhere->push_back(varWhere);

    QString valorWhere = QString::fromStdString(vectorInstruccion[3]);
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

void MainWindow::insertarEnTabla(vector<string> elementos){
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

//INSERT INTO METADATA(name,author,date,size)
//VALUES("pikachuBailando.png", "Ash Ketchup", "2019", "2MB");

//SELECT name, author FROM METADATA
//WHERE date = "2019";

//DELETE FROM METADATA WHERE date = "2019";

//UPDATE METADATA
//SET date = "2020", author = "Brock"
//WHERE name = "pikachuBailando.png";
