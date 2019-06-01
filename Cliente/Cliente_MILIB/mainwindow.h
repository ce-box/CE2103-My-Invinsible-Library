#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <string>
#include <QString>
#include <QColor>
#include <QColorDialog>
#include <QTextEdit>
#include <qdebug.h>
#include <QFileDialog>

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
    ~MainWindow();

private:
    Ui::MainWindow *ui;
};

#endif // MAINWINDOW_H
