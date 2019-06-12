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
 * @brief The Client class takes care of the connections to
 * the Web Services, using the HTTP protocol verbs like GET,
 * POST, PUT and DELETE
 */
class Client
{

public:

    /**
     * @brief Client constructor
     * @param ip[in] : IP address of the target web Service
     * @param port[in] : Comunication port of the target web Service
     * @param path[in] : Url associated with the request
     */
    Client(QString ip,QString port,QString path);

    /**
     * @brief Gives new values ​​to the network configuration
     * @param ip[in] : IP address of the target web Service
     * @param port[in] : Comunication port of the target web Service
     * @param path[in] : Url associated with the Web Service
     */
    void updateInfo(QString ip,QString port,QString path);

    /**
     * @brief The POST verb is most-often utilized to **create** new resources
     * @param path[in] : Url associated with the request
     * @param jsonDoc[in] : JSON that contains the data to send
     * @return Received data in JSON (String) format
     */
    QString POST(const QString &path, const QString &jsonDoc = "");

    /**
     * @brief The HTTP GET method is used to **read** (or retrieve) a
     * representation of a resource.
     * @param path[in] : Url associated with the request
     * @param jsonDoc[in] : JSON that contains the data to send
     * @return Received data in JSON (String) format
     */
    QString GET(const QString &path, const QString &jsonDoc = "");

    /**
     * @brief PUT is most-often utilized for **update** capabilities
     * @param path[in] : Url associated with the request
     * @param jsonDoc[in] : JSON that contains the data to send
     * @return void
     */
    QString PUT(const QString &path, const QString &jsonDoc = "");

    /**
     * @brief DELETE is pretty easy to understand. It is used to **delete**
     * a resource identified by a URI.
     * @param path[in] : Url associated with the request
     * @param jsonDoc[in] : JSON that contains the data to send
     * @return Received data in JSON (String) format
     */
    QString DELETE(const QString &path, const QString &jsonDoc = "");

private:

    QString ip;
    QString port;
    QString path;
    QString defaultPath;
};

#endif // CLIENT_H
