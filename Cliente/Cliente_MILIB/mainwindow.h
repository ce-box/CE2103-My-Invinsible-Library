#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <string>
#include <vector>
#include <boost/algorithm/string.hpp>
#include <QMainWindow>
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
    void insertarEnTabla(std::vector<std::string> elementos);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    void configurarNombreColumnas(std::vector<std::string> elementos);
    void insertarFilas(std::vector<std::string> elementos);
};

#endif // MAINWINDOW_H
