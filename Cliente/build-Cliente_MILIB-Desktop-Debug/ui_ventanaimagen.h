/********************************************************************************
** Form generated from reading UI file 'ventanaimagen.ui'
**
** Created by: Qt User Interface Compiler version 5.7.1
**
** WARNING! All changes made in this file will be lost when recompiling UI file!
********************************************************************************/

#ifndef UI_VENTANAIMAGEN_H
#define UI_VENTANAIMAGEN_H

#include <QtCore/QVariant>
#include <QtWidgets/QAction>
#include <QtWidgets/QApplication>
#include <QtWidgets/QButtonGroup>
#include <QtWidgets/QDialog>
#include <QtWidgets/QHeaderView>
#include <QtWidgets/QLabel>

QT_BEGIN_NAMESPACE

class Ui_VentanaImagen
{
public:
    QLabel *imageLabel;

    void setupUi(QDialog *VentanaImagen)
    {
        if (VentanaImagen->objectName().isEmpty())
            VentanaImagen->setObjectName(QStringLiteral("VentanaImagen"));
        VentanaImagen->resize(1200, 700);
        imageLabel = new QLabel(VentanaImagen);
        imageLabel->setObjectName(QStringLiteral("imageLabel"));
        imageLabel->setGeometry(QRect(10, 10, 1181, 681));

        retranslateUi(VentanaImagen);

        QMetaObject::connectSlotsByName(VentanaImagen);
    } // setupUi

    void retranslateUi(QDialog *VentanaImagen)
    {
        VentanaImagen->setWindowTitle(QApplication::translate("VentanaImagen", "Dialog", Q_NULLPTR));
        imageLabel->setText(QString());
    } // retranslateUi

};

namespace Ui {
    class VentanaImagen: public Ui_VentanaImagen {};
} // namespace Ui

QT_END_NAMESPACE

#endif // UI_VENTANAIMAGEN_H
