#include "mainwindow.h"
#include "ui_mainwindow.h"
using namespace std;

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow){
    ui->setupUi(this);
    colorearWidget(ui->ideTextEdit, "#373753");
    colorearWidget(ui->appOutputTextEdit, "#373753");
    ui->mainFrame->setStyleSheet(" .QFrame { background-color : #464646 } ");
    ui->AppOutputLabel->setStyleSheet("QLabel { color : white; }");
}

void MainWindow::colorearWidget(QWidget* widget, QString colorFondo){
    QPalette paleta = widget->palette();
    paleta.setColor(QPalette::Base, colorFondo);
    paleta.setColor(QPalette::Text, Qt::white);
    widget->setPalette(paleta);
}

MainWindow::~MainWindow(){
    delete ui;
}
