#ifndef VENTANAIMAGEN_H
#define VENTANAIMAGEN_H

#include <QDialog>

namespace Ui {
class VentanaImagen;
}

class VentanaImagen : public QDialog
{
    Q_OBJECT

public:
    explicit VentanaImagen(QWidget *parent = 0);
    ~VentanaImagen();

private:
    Ui::VentanaImagen *ui;
};

#endif // VENTANAIMAGEN_H
