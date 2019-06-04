#include "ventanaimagen.h"
#include "ui_ventanaimagen.h"

VentanaImagen::VentanaImagen(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::VentanaImagen){
    ui->setupUi(this);
}

void VentanaImagen::setImagen(QPixmap imagen){
    int w = ui->imageLabel->width();
    int h = ui->imageLabel->height();
    ui->imageLabel->setPixmap(imagen.scaled(w,h,Qt::KeepAspectRatio));
}

VentanaImagen::~VentanaImagen()
{
    delete ui;
}
