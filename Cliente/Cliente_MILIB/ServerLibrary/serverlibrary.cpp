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
    this->IP = "localhost";
    this->Port = "8080";
    this->defaultUrl = "http//"+IP+":"+Port;

}

//----------------------------------------------------------------------
//                            SETTINGS
//----------------------------------------------------------------------

// Configuration of the necessary information for the connection
void ServerLibrary::setServer(QString url, QString ip, QString port){
    this->defaultUrl = url;
    this->Port = port;
    this->IP = ip;
}


// Returns and prints API information on the screen
void ServerLibrary::getServerInfo(){
    qDebug()<< "--------------------------";
    qDebug()<< "        MILIB API         ";
    qDebug()<< "--------------------------";
    qDebug()<< "* URL:"<< this->defaultUrl;
    qDebug()<< "* PORT:"<< this->Port;
    qDebug()<< "* IP ADDR:"<< this->IP;
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

// START
void ServerLibrary::START(){
    Client* client = new Client(this->IP,this->Port,this->defaultUrl);
    client->POST("/start");
    delete(client);
}

// INSERT
void ServerLibrary::INSERT(QString MetaJson){

    Client* client = new Client(this->IP,this->Port,this->defaultUrl);
    client->POST("/insert",MetaJson);

    delete(client);
}

// SELECT
QString ServerLibrary::SELECT(QString MetaJson){

    Client* client = new Client(this->IP,this->Port,this->defaultUrl);
    QString response = client->GET("/select",MetaJson);

    delete(client);
    return response;
}

// UPDATE
void ServerLibrary::UPDATE(QString MetaJson){

    Client* client = new Client(this->IP,this->Port,this->defaultUrl);
    client->PUT("/update",MetaJson);

    delete(client);
}

// DELETE
void ServerLibrary::DELETE(QString MetaJson){

    Client* client = new Client(this->IP,this->Port,this->defaultUrl);
    client->DELETE("/delete",MetaJson);

    delete(client);
}

// COMMIT
void ServerLibrary::COMMIT(){

    Client* client = new Client(this->IP,this->Port,this->defaultUrl);
    client->PUT("/commit");

    delete(client);
}

// ROLLBACK
void ServerLibrary::BACK(){

    Client* client = new Client(this->IP,this->Port,this->defaultUrl);
    client->PUT("/back");

    delete(client);
}


