#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <string>
#include <QString>
#include <QImage>
#include <QColor>
#include <QColorDialog>
#include <QTextEdit>
#include <qdebug.h>
#include <QFileDialog>
#include <QByteArray>
#include <QBuffer>
#include <QFile>
#include <iostream>
#include <ventanaimagen.h>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

private slots:
    void abrirExploradorArchivos();
    void obtenerInputIDE();

public:
    explicit MainWindow(QWidget *parent = 0);
    void colorearWidget(QWidget* textEdit, QString colorFondo);
    void visualizarImagen(std::string data);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
};

#endif // MAINWINDOW_H
