#include "mainwindow.h"
#include <QApplication>
#include <QtCore/QJsonDocument>
#include <QtCore/QJsonObject>
#include <QtCore/QJsonArray>
#include "ServerLibrary/client.h"
#include "ServerLibrary/serverlibrary.h"

/*
 * Este método deben recibir un objeto (listas) lo parsea y retorna el QString del JSON
 */
void insertTest (ServerLibrary* server){
    QJsonArray slot;
    slot.append("name");
    slot.append("author");
    slot.append("date");
    slot.append("size");
    slot.append("description");

    QJsonArray slotValues;
    slotValues.append("imagenChuza");
    slotValues.append("Esteban");
    slotValues.append("2012");
    slotValues.append("1000");
    slotValues.append("Beautiful pic");

    QJsonObject jsonObj;
    jsonObj.insert("slots",slot);
    jsonObj.insert("slotsValues",slotValues);

    QJsonDocument jsonDoc(jsonObj);
    QByteArray jsonB = jsonDoc.toJson();
    QString jsonQStr = QString(jsonB);
    string json = jsonQStr.toStdString();

    cout<<json<<endl;
    qDebug()<<jsonQStr;

    server->INSERT(jsonQStr);
}

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);
    MainWindow w;
    w.show();

    ServerLibrary* server = ServerLibrary::getServer();
    server->setMilib("/MILIB_Servidor_war_exploded/api/database","192.168.0.21");
    server->setRaid("/MILIB_RAID_war_exploded/api/raid","192.168.0.21");
    server->getMilibInfo();
    server->getRaidInfo();

    // Primero se debe iniciar el server
    server->START();

    // Prueba del INSERT
    insertTest(server);

    /*
    server->INSERT(jsonStr);
    server->SELECT(jsonStr);
    server->UPDATE(jsonStr);
    server->DELETE(jsonStr);
    server->COMMIT();
    server->BACK();*/

    return a.exec();
}
