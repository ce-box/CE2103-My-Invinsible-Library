#include "mainwindow.h"
#include <QApplication>
#include <QtCore/QJsonDocument>
#include <QtCore/QJsonObject>
#include "Client/client.h"

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();

    QJsonObject jsonObj;
    jsonObj.insert("request",2);
    jsonObj.insert("columnas","id,data");
    jsonObj.insert("valueA","1,2000");

    QJsonDocument jsonDoc(jsonObj);
    QByteArray jsonB = jsonDoc.toJson();
    QString jsonStr = QString(jsonB);

    Client* client = new Client();
    client->POST("/insert",jsonStr);

    return a.exec();
}
