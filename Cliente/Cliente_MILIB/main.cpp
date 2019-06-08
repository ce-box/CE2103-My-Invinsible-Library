#include "mainwindow.h"
#include <QApplication>
#include <QtCore/QJsonDocument>
#include <QtCore/QJsonObject>
#include <QtCore/QJsonArray>
#include "ServerLibrary/client.h"
#include "ServerLibrary/serverlibrary.h"
#include "json/jsonserializer.h"
#include "Data_Structures/lista.hpp"

/*
 * Este método deben recibir un objeto (listas) lo parsea y retorna el QString del JSON
 */
void insertTest1 (ServerLibrary* server){

    Lista<QString> *slotsList =new Lista<QString>;
    Lista<QString> *valuesList =new Lista<QString>;

    slotsList->push_back("name");
    //slotsList->push_back("author");
    slotsList->push_back("date");
    slotsList->push_back("size");
    //slotsList->push_back("description");

    valuesList->push_back("img1");
    //valuesList->push_back("Esteban");
    valuesList->push_back("2012");
    valuesList->push_back("1000");
    //valuesList->push_back("Cool! :)");

    QString jsonQStr = JsonSerializer::insertJSON(slotsList,valuesList);

    cout<<jsonQStr.toStdString()<<endl;

    server->INSERT(jsonQStr);
}

void insertTest2 (ServerLibrary* server){
    Lista<QString> *slotsList =new Lista<QString>;
    Lista<QString> *valuesList =new Lista<QString>;

    slotsList->push_back("name");
    slotsList->push_back("author");
    slotsList->push_back("date");
    slotsList->push_back("size");
    //slotsList->push_back("description");

    valuesList->push_back("img2");
    valuesList->push_back("Erick Barrantes");
    valuesList->push_back("1998");
    valuesList->push_back("1024");
    //valuesList->push_back("Cool! :)");

    QString jsonQStr = JsonSerializer::insertJSON(slotsList,valuesList);

    cout<<jsonQStr.toStdString()<<endl;

    server->INSERT(jsonQStr);
}
// Enviando los tres valores
void selectTest1 (ServerLibrary* server){
    Lista<QString> *slotsList =new Lista<QString>;
    Lista<QString> *whereList =new Lista<QString>;
    Lista<QString> *whereValuesList =new Lista<QString>;

    slotsList->push_back("name");
    slotsList->push_back("author");
    slotsList->push_back("date");
    slotsList->push_back("size");
    slotsList->push_back("description");

    whereList->push_back("author");
    whereValuesList->push_back("Esteban");

    QString jsonQStr = JsonSerializer::selectJSON(slotsList,whereList,whereValuesList);

    cout<<jsonQStr.toStdString()<<endl;

    server->SELECT(jsonQStr);
}

// Enviando Slot vacío
void selectTest2 (ServerLibrary* server){
    Lista<QString> *slotsList =new Lista<QString>;
    Lista<QString> *whereList =new Lista<QString>;
    Lista<QString> *whereValuesList =new Lista<QString>;

    whereList->push_back("author");
    whereValuesList->push_back("Esteban");

    QString jsonQStr = JsonSerializer::selectJSON(slotsList,whereList,whereValuesList);

    cout<<jsonQStr.toStdString()<<endl;

    server->SELECT(jsonQStr);
}

// Solo enviando Slots
void selectTest3 (ServerLibrary* server){
    Lista<QString> *slotsList =new Lista<QString>;

    slotsList->push_back("name");
    slotsList->push_back("author");
    slotsList->push_back("date");

    QString jsonQStr = JsonSerializer::selectJSON(slotsList);

    cout<<jsonQStr.toStdString()<<endl;

    server->SELECT(jsonQStr);
}

// Enviando vacío :: Da en Java un nullptr exception
void selectTest4 (ServerLibrary* server){

    QString jsonQStr = JsonSerializer::selectJSON();

    cout<<jsonQStr.toStdString()<<endl;

    server->SELECT(jsonQStr);
}

// Enviando request con between
void selectTest5 (ServerLibrary* server){
    Lista<QString> *slotsList =new Lista<QString>;
    Lista<QString> *whereList =new Lista<QString>;
    Lista<QString> *whereValuesAList =new Lista<QString>;
    Lista<QString> *whereValuesBList =new Lista<QString>;

    slotsList->push_back("name");
    slotsList->push_back("author");
    slotsList->push_back("date");

    whereList->push_back("ID");
    whereValuesAList->push_back("1");
    whereValuesBList->push_back("2");

    QString jsonQStr = JsonSerializer::selectJSON(slotsList,whereList,whereValuesAList,whereValuesBList);

    cout<<jsonQStr.toStdString()<<endl;

    server->SELECT(jsonQStr);
}

// Haciendo un Update
void updateTest(ServerLibrary* server){
    Lista<QString> *slotsList =new Lista<QString>;
    Lista<QString> *valuesList =new Lista<QString>;
    Lista<QString> *whereList =new Lista<QString>;
    Lista<QString> *whereValuesList =new Lista<QString>;

    slotsList->push_back("name");
    slotsList->push_back("description");

    valuesList->push_back("El churro gigante");
    valuesList->push_back("la vara promete");

    whereList->push_back("author");
    whereValuesList->push_back("Esteban");

    QString jsonQStr = JsonSerializer::updateJSON(slotsList,valuesList,whereList,whereValuesList);

    cout<<jsonQStr.toStdString()<<endl;

    server->UPDATE(jsonQStr);
}

// Eliminando una imagen
void deleteTest(ServerLibrary* server){
    Lista<QString> *whereList =new Lista<QString>;
    Lista<QString> *whereValuesList =new Lista<QString>;

    whereList->push_back("author");
    whereValuesList->push_back("Esteban");

    QString jsonQStr = JsonSerializer::deleteJSON(whereList,whereValuesList);

    cout<<jsonQStr.toStdString()<<endl;

    server->DELETE(jsonQStr);
}

int main(int argc, char *argv[])
{
    QApplication a(argc, argv);

    ServerLibrary* server = ServerLibrary::getServer();
    server->setMilib("/MILIB_Servidor_war_exploded/api/database","192.168.100.20");
    server->setRaid("/MILIB_RAID_war_exploded/api/raid","192.168.100.20");
    server->getMilibInfo();
    server->getRaidInfo();

    // Primero se debe iniciar el server
    server->START();

    // Prueba del INSERT -> Funcionan todas las condiciones
    insertTest1(server);
    insertTest2(server);

    server->COMMIT();

    // Pruebas del SELECT -> Funcionan todas las condiciones
    //selectTest1(server);
    //selectTest2(server);
    //selectTest3(server);
    //selectTest4(server);
    selectTest5(server);

    // Pruebas del UPDATE -> Funcionan todas las condiciones
    //updateTest(server);

    //server->COMMIT();

    // Pruebas del DELETE
    //deleteTest(server);
    //server->COMMIT();

    return a.exec();
}
