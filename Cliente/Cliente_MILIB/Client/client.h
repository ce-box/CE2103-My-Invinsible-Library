#ifndef CLIENT_H
#define CLIENT_H

// Libraries
#include <iostream>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QNetworkRequest>
#include <QObject>
#include <QUrl>

class Client
{

public:

    Client(QString ip,QString port);

    QString POST(const QString &path, const QString &jsonDoc);
    QString GET(const QString &path, const QString &jsonDoc);
    void PUT(const QString &path, const QString &jsonDoc);
    QString DELETE(const QString &path, const QString &jsonDoc);

private:

    QString ip;
    QString port;
    QString path;
    QString defaultPath;
};

#endif // CLIENT_H
