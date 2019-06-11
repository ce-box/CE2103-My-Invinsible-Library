#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <string>
#include <vector>
#include <boost/algorithm/string.hpp>
#include <iostream>
#include <ventanaimagen.h>
#include <lectorsintaxis.h>
#include <ServerLibrary/serverlibrary.h>
#include <Data_Structures/lista.hpp>
#include <json/jsonserializer.h>
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
#include <QInputDialog>

namespace Ui {
class MainWindow;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

private slots:
    void abrirExploradorArchivos();
    void obtenerInputIDE();
    void commit();
    void rollback();

public:
    explicit MainWindow(QWidget *parent = 0);
    void colorearWidget(QWidget* textEdit, QString colorFondo);
    void visualizarImagen(std::string data);
    void insertarEnTabla(std::vector<std::string> elementos);
    void instruccionInsert(std::vector<std::string> vectorInstruccion);
    void instruccionSelect(std::vector<std::string> vectorInstruccion);
    void instruccionDelete(std::vector<std::string> vectorInstruccion);
    void instruccionUpdate(std::vector<std::string> vectorInstruccion);
    ~MainWindow();

private:
    Ui::MainWindow *ui;
    void configurarNombreColumnas(std::vector<std::string> elementos);
    void insertarFilas(std::vector<std::string> elementos);
    std::string imagenCargada;
    QString galeriaIngresada;
    QString sizeImagen;
};

#endif // MAINWINDOW_H
