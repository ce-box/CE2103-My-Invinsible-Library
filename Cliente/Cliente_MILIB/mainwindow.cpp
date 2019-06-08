#include "mainwindow.h"
#include "ui_mainwindow.h"
using namespace std;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow){
    ui->setupUi(this);

    colorearWidget(ui->ideTextEdit, "#373753");
    colorearWidget(ui->appOutputTextEdit, "#373753");

    ui->mainFrame->setStyleSheet(".QFrame { background-color : #464646 } ");
    ui->AppOutputLabel->setStyleSheet("QLabel { color : white }");

    connect(ui->subirImagenPushButton, SIGNAL (clicked()), this, SLOT (abrirExploradorArchivos()));
    connect(ui->runPushButton, SIGNAL (clicked()), this, SLOT (obtenerInputIDE()));

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

    QImage imagen;
    imagen.load(imgDireccion, "PNG");

    QByteArray byteArray;
    QBuffer buffer(&byteArray);
    imagen.save(&buffer, "PNG");

    QByteArray byteArray64 = byteArray.toBase64();
    string data = byteArray64.toStdString();

    visualizarImagen(data);
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


MainWindow::~MainWindow(){
    delete ui;
}
