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

    Client();

    QString POST(const QString &path, const QString &jsonDoc);

private:

    QString ip;
    QString port;
    QString path;
    QString defaultPath;
};

#endif // CLIENT_H
