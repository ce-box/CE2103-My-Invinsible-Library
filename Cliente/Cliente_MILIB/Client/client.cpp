#include "client.h"
#include <QEventLoop>

// POST Method
QString Client::POST(const QString &path, const QString &jsonDoc){

    QNetworkAccessManager *manager = new QNetworkAccessManager();
    QUrl url(this->defaultPath + path);

    QNetworkRequest request;
    request.setUrl(url);
    request.setRawHeader("Content-Type", "application/json");

    qDebug() << "Client POST request to: " << url.toString();
    qDebug() << jsonDoc;

    //manager->post(request,jsonDoc.toUtf8());

    QNetworkReply *reply = manager->post(request,jsonDoc.toUtf8());

    // Esto es necesario para leer la entrada
    QEventLoop loop;
    QObject::connect(reply, SIGNAL(finished()), &loop, SLOT(quit()));
    loop.exec();
    QString recv = (QString)reply->readAll();

    qDebug()<<"Data received: "<< recv;

    return recv;
}

Client::Client(){
    ip = "localhost";
    port = "8080";
    path = "/MILIB_Servidor_war_exploded/api";
    defaultPath = "http://localhost:8080/MILIB_Servidor_war_exploded/api";
}

/*
 *
 */
