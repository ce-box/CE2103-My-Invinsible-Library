#include "mainwindow.h"
#include <QApplication>
#include <QtCore/QJsonDocument>
#include <QtCore/QJsonObject>
#include "ServerLibrary/client.h"

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

    // Con la librería se crean los clientes y se liberan con cada request
    qDebug()<< "METADATA ------------------------------";

    Client* client = new Client("localhost","8080","/MILIB_Servidor_war_exploded/api/database");
    client->POST("/insert",jsonStr);
    client->GET("/select",jsonStr);
    client->PUT("/update",jsonStr);
    client->DELETE("/delete",jsonStr);
    client->PUT("/commit","");
    client->PUT("/back","");
    delete(client);

    qDebug()<< "RAID------------------------------";
    Client* client2 = new Client("localhost","9080","/MILIB_RAID_war_exploded/api/raid");
    client2->POST("/insert",jsonStr);
    client2->GET("/select",jsonStr);
    client2->DELETE("/delete",jsonStr);
    client2->PUT("/commit","");
    client2->PUT("/back","");
    delete(client2);

    return a.exec();
}
