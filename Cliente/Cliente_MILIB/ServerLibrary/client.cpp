#include "client.h"
#include <QEventLoop>

// POST Method
QString Client::POST(const QString &path, const QString &jsonDoc){

    QNetworkAccessManager *manager = new QNetworkAccessManager();
    QUrl url(this->defaultPath + path);

    QNetworkRequest request;
    request.setUrl(url);
    request.setRawHeader("Content-Type", "application/json");

    qDebug() << "[POST] Client POST request to: " << url.toString();
    qDebug() << "[POST] Data sent: " <<jsonDoc;

    //manager->post(request,jsonDoc.toUtf8());

    QNetworkReply *reply = manager->post(request,jsonDoc.toUtf8());

    // Esto es necesario para leer la entrada
    QEventLoop loop;
    QObject::connect(reply, SIGNAL(finished()), &loop, SLOT(quit()));
    loop.exec();
    QString recv = (QString)reply->readAll();

    qDebug()<<"[POST] Data received: "<< recv;

    return recv;
}

// GET Method
QString Client::GET(const QString &path, const QString &jsonDoc){

    QNetworkAccessManager *manager = new QNetworkAccessManager();
    QUrl url(this->defaultPath + path);

    QNetworkRequest request;
    request.setUrl(url);
    request.setRawHeader("Content-Type", "application/json");

    qDebug() << "[GET] Client GET request to: " << url.toString();
    qDebug() << "[GET] Data sent: " <<jsonDoc;

    QNetworkReply *reply = manager->sendCustomRequest(
                request,"GET",jsonDoc.toUtf8());

    // Esto es necesario para leer la entrada
    QEventLoop loop;
    QObject::connect(reply, SIGNAL(finished()), &loop, SLOT(quit()));
    loop.exec();
    QString recv = (QString)reply->readAll();

    qDebug()<<"[GET] Data received: "<< recv;

    return recv;
}

// PUT Method
void Client::PUT(const QString &path, const QString &jsonDoc){

    QNetworkAccessManager *manager = new QNetworkAccessManager();
    QUrl url(this->defaultPath + path);

    QNetworkRequest request;
    request.setUrl(url);
    request.setRawHeader("Content-Type", "application/json");

    qDebug() << "[PUT] Client POST request to: " << url.toString();
    qDebug() << "[PUT] Data sent: " << jsonDoc;

    manager->put(request,jsonDoc.toUtf8());

    return;
}

// DELETE Method
QString Client::DELETE(const QString &path, const QString &jsonDoc){

    QNetworkAccessManager *manager = new QNetworkAccessManager();
    QUrl url(this->defaultPath + path);

    QNetworkRequest request;
    request.setUrl(url);
    request.setRawHeader("Content-Type", "application/json");

    qDebug() << "[DELETE] Client POST request to: " << url.toString();
    qDebug() << "[DELETE] Data sent: " << jsonDoc;

    QNetworkReply *reply = manager->sendCustomRequest(
                request,"DELETE",jsonDoc.toUtf8());

    // Esto es necesario para leer la entrada
    QEventLoop loop;
    QObject::connect(reply, SIGNAL(finished()), &loop, SLOT(quit()));
    loop.exec();
    QString recv = (QString)reply->readAll();

    qDebug()<<"[DELETE] Data received: "<< recv;

    return recv;
}


// Constructor

Client::Client(QString ip,QString port,QString path){
    this->ip = ip;
    this->port = port;
    this->path = path;
    defaultPath = "http://"+ip+":"+port+path;
}

