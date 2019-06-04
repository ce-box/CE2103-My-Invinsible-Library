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


    //EJEMPLO TABLA
    ui->metadataTable->setColumnCount(4);
    ui->metadataTable->setHorizontalHeaderLabels(QStringList() << tr("NOMBRE")
                                                               << tr("ARTISTA")
                                                               << tr("DURACION")
                                                               << tr("ALBUM"));
    int tamanioCol = (510-11)/4;
    ui->metadataTable->horizontalHeader()->resizeSection(0, tamanioCol);
    ui->metadataTable->horizontalHeader()->resizeSection(1, tamanioCol);
    ui->metadataTable->horizontalHeader()->resizeSection(2, tamanioCol);
    ui->metadataTable->horizontalHeader()->resizeSection(3, tamanioCol);

    ui->metadataTable->setRowCount(1);

    ui->metadataTable->setItem(0, 0, new QTableWidgetItem("Karma Police"));
    ui->metadataTable->setItem(0, 1, new QTableWidgetItem("Radiohead"));
    ui->metadataTable->setItem(0, 2, new QTableWidgetItem("4:27"));
    ui->metadataTable->setItem(0, 3, new QTableWidgetItem("OK Computer"));
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
    QString inputIDE = ui->ideTextEdit->toPlainText();
    qDebug()<<inputIDE;
}


MainWindow::~MainWindow(){
    delete ui;
}
