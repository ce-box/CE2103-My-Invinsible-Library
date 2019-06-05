#include "mainwindow.h"
#include <QApplication>
#include <QtCore/QJsonDocument>
#include <QtCore/QJsonObject>
#include "ServerLibrary/client.h"
#include "ServerLibrary/serverlibrary.h"

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

    ServerLibrary* server = ServerLibrary::getServer();

    // Here i'm using the default ports
    server->setMilib("/MILIB_Servidor_war_exploded/api/database","192.168.0.21");
    server->setRaid("/MILIB_RAID_war_exploded/api/raid","192.168.0.21");

    server->getMilibInfo();
    server->getRaidInfo();

    server->INSERT(jsonStr);
    server->SELECT(jsonStr);
    server->UPDATE(jsonStr);
    server->DELETE(jsonStr);
    server->COMMIT();
    server->BACK();

    return a.exec();
}
