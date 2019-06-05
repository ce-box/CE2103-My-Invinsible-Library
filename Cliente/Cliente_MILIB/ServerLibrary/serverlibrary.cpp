#include "serverlibrary.h"

//----------------------------------------------------------------------
//                          BUILD AND INIT
//----------------------------------------------------------------------

// Singleton del ServerLibrary
ServerLibrary* ServerLibrary::server = nullptr;

ServerLibrary* ServerLibrary::getServer(){
    if(!server) server = new ServerLibrary();
    return server;
}

// Constructor
ServerLibrary::ServerLibrary(){
    // Setea los valores default del proyecto
    this->MilibIP = "localhost";
    this->MilibPort = "8080";
    this->MilibUrl = "http//localhost:8080";

    this->RaidIP = "localhost";
    this->RaidPort = "9080";
    this->RaidUrl = "http//localhost:9080";

}

//----------------------------------------------------------------------
//                            SETTINGS
//----------------------------------------------------------------------

// Configuration of the necessary information for the connection
void ServerLibrary::setMilib(QString url, QString ip, QString port){
    this->MilibUrl = url;
    this->MilibPort = port;
    this->MilibIP = ip;
}

void ServerLibrary::setRaid(QString url, QString ip, QString port){
    this->RaidUrl = url;
    this->RaidPort = port;
    this->RaidIP = ip;
}

// Returns and prints API information on the screen
void ServerLibrary::getMilibInfo(){
    qDebug()<< "--------------------------";
    qDebug()<< "        MILIB API         ";
    qDebug()<< "--------------------------";
    qDebug()<< "* URL:"<< this->MilibUrl;
    qDebug()<< "* PORT:"<< this->MilibPort;
    qDebug()<< "* IP ADDR:"<< this->MilibIP;
    qDebug()<< "--------------------------\n";
}

void ServerLibrary::getRaidInfo(){
    qDebug()<< "--------------------------";
    qDebug()<< "         RAID API         ";
    qDebug()<< "--------------------------";
    qDebug()<< "* URL:"<< this->RaidUrl;
    qDebug()<< "* PORT:"<< this->RaidPort;
    qDebug()<< "* IP ADDR:"<< this->RaidIP;
    qDebug()<< "--------------------------\n";
}

//----------------------------------------------------------------------
//                      CONNECTION METHODS
//----------------------------------------------------------------------

// Interface that connects GUI with RAID and METADATA DATABASE

/*
 * Each one of the methods works as an interface for the realization of
 * requests to the Web Services of RAID and Metadata and they work in a
 * similar way. These are the steps:

    #1. A client with the MILIB configuration is instantiated.
    #2. The request is made to MILIB.
    #3. The configuration is updated to RAID.
    #4. The RAID request is made.
    #5. The created Client instance is deleted.
 */

// INSERT
void ServerLibrary::INSERT(QString MetaJson){

    Client* client = new Client(this->MilibIP,this->MilibPort,this->MilibUrl);
    client->POST("/insert",MetaJson);

    client->updateInfo(this->RaidIP,this->RaidPort,this->RaidUrl);
    client->POST("/insert",MetaJson);

    delete(client);
}

// SELECT
QString ServerLibrary::SELECT(QString MetaJson){

    Client* client = new Client(this->MilibIP,this->MilibPort,this->MilibUrl);
    client->GET("/select",MetaJson);

    client->updateInfo(this->RaidIP,this->RaidPort,this->RaidUrl);
    client->GET("/select",MetaJson);

    delete(client);
    return "Hi";
}

// UPDATE
void ServerLibrary::UPDATE(QString MetaJson){

    Client* client = new Client(this->MilibIP,this->MilibPort,this->MilibUrl);
    client->PUT("/update",MetaJson);

    delete(client);
}

// DELETE
void ServerLibrary::DELETE(QString MetaJson){

    Client* client = new Client(this->MilibIP,this->MilibPort,this->MilibUrl);
    client->DELETE("/delete",MetaJson);

    client->updateInfo(this->RaidIP,this->RaidPort,this->RaidUrl);
    client->DELETE("/delete",MetaJson);

    delete(client);
}

// COMMIT
void ServerLibrary::COMMIT(){

    Client* client = new Client(this->MilibIP,this->MilibPort,this->MilibUrl);
    client->PUT("/commit");

    client->updateInfo(this->RaidIP,this->RaidPort,this->RaidUrl);
    client->PUT("/commit");

    delete(client);
}

// ROLLBACK
void ServerLibrary::BACK(){

    Client* client = new Client(this->MilibIP,this->MilibPort,this->MilibUrl);
    client->PUT("/back");

    client->updateInfo(this->RaidIP,this->RaidPort,this->RaidUrl);
    client->PUT("/back");

    delete(client);
}


