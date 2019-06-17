#ifndef VENTANAIMAGEN_H
#define VENTANAIMAGEN_H

#include <QDialog>
#include <QImage>
#include <QPixmap>
#include <qdebug.h>

namespace Ui {
class VentanaImagen;
}

class VentanaImagen : public QDialog
{
    Q_OBJECT

public:
    explicit VentanaImagen(QWidget *parent = 0);
    void setImagen(QPixmap imagen);
    ~VentanaImagen();

private:
    Ui::VentanaImagen *ui;
};

#endif // VENTANAIMAGEN_H
