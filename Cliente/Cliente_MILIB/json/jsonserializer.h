#ifndef JSONSERIALIZER_H
#define JSONSERIALIZER_H

#define username "Esteban"

// Libraries
#include <QtCore/QJsonArray>
#include <QtCore/QJsonDocument>
#include <QtCore/QJsonObject>

#include "../Data_Structures/lista.hpp"

/**
 * @brief The JsonSerializer class
 */
class JsonSerializer
{
public:

    /**
     * @brief startJSON
     * @param slotsList
     * @param valuesList
     * @return
     */
    static QString startJSON();


    /**
     * @brief insertJSON
     * @param slotsList
     * @param valuesList
     * @return
     */
    static QString insertJSON(Lista<QString> *slotsList,Lista<QString> *valuesList,QString img64);

    /**
     * @brief selectJSON
     * @param slotsList
     * @param whereList
     * @param whereValuesList
     * @return
     */
    static QString selectJSON(Lista<QString> *slotsList = new Lista<QString>,
                              Lista<QString> *whereList = new Lista<QString>,
                              Lista<QString> *whereValuesAList = new Lista<QString>,
                              Lista<QString> *whereValuesBList = new Lista<QString>);

    /**
     * @brief selectImgJSON
     * @param ID
     * @return
     */
    static QString selectImgJSON(QString ID);

    /**
     * @brief updateJSON
     * @param slotsList
     * @param valuesList
     * @param whereList
     * @param whereValuesList
     * @return
     */
    static QString updateJSON(Lista<QString> *slotsList,Lista<QString> *valuesList,Lista<QString> *whereList,Lista<QString> *whereValuesList);

    /**
     * @brief deleteJSON
     * @param whereList
     * @param whereValuesList
     * @return
     */
    static QString deleteJSON(Lista<QString> *whereList,Lista<QString> *whereValuesList);
};

#endif // JSONSERIALIZER_H
