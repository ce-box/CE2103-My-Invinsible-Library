#ifndef CLIENT_H
#define CLIENT_H

// Libraries
#include <iostream>
#include <QNetworkAccessManager>
#include <QNetworkReply>
#include <QNetworkRequest>
#include <QObject>
#include <QUrl>

/**
 * @brief The Client class
 */
class Client
{

public:

    /**
     * @brief Client
     * @param ip
     * @param port
     */
    Client(QString ip,QString port,QString path);

    /**
     * @brief POST
     * @param path
     * @param jsonDoc
     * @return
     */
    QString POST(const QString &path, const QString &jsonDoc);

    /**
     * @brief GET
     * @param path
     * @param jsonDoc
     * @return
     */
    QString GET(const QString &path, const QString &jsonDoc);

    /**
     * @brief PUT
     * @param path
     * @param jsonDoc
     */
    void PUT(const QString &path, const QString &jsonDoc);

    /**
     * @brief DELETE
     * @param path
     * @param jsonDoc
     * @return
     */
    QString DELETE(const QString &path, const QString &jsonDoc);

private:

    QString ip;
    QString port;
    QString path;
    QString defaultPath;
};

#endif // CLIENT_H
