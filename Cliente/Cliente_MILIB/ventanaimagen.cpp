#include "ventanaimagen.h"
#include "ui_ventanaimagen.h"

VentanaImagen::VentanaImagen(QWidget *parent) :
    QDialog(parent),
    ui(new Ui::VentanaImagen)
{
    ui->setupUi(this);
}

VentanaImagen::~VentanaImagen()
{
    delete ui;
}
