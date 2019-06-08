#include "jsonserializer.h"

// Serializer the information to INSERT in JSON format
QString JsonSerializer::insertJSON(Lista<QString> *slotsList, Lista<QString> *valuesList){

    // Step 1: Creates two JsonArray w/ the Data of both list
    // ADVICE: Both lists must have the same size!!

    QJsonArray slot;
    QJsonArray slotValues;
    for(int i = 0; i < slotsList->size();i++){
        slot.append(slotsList->get_index(i));
        slotValues.append(valuesList->get_index(i));
    }

    // Step 2: Add those JsonArray  and create a JSON Object
    QJsonObject jsonObj;
    jsonObj.insert("slots",slot);
    jsonObj.insert("slotsValues",slotValues);

    // Step 3: Convert the JsonDoc into QString
    QJsonDocument jsonDoc(jsonObj);
    QByteArray jsonB = jsonDoc.toJson();
    QString jsonQStr = QString(jsonB);

    return jsonQStr;
}

// Serializer the information to SELECT in JSON format
QString JsonSerializer::selectJSON(Lista<QString> *slotsList,
                                   Lista<QString> *whereList,
                                   Lista<QString> *whereValuesList){
    // Step 1: Creates three JsonArray w/ the Data of the list
    // ADVICE: Both lists (where and whereValues) must have the same size!!

    QJsonArray slot;
    QJsonArray where;
    QJsonArray whereValues;

    for(int i = 0; i < slotsList->size();i++){
        slot.append(slotsList->get_index(i));
    }

    for(int i = 0; i < whereList->size();i++){
        where.append(whereList->get_index(i));
        whereValues.append(whereValuesList->get_index(i));
    }

    // Step 2: Add those JsonArray  and create a JSON Object
    QJsonObject jsonObj;
    jsonObj.insert("slots",slot);
    jsonObj.insert("where",where);
    jsonObj.insert("whereValues",whereValues);

    // Step 3: Convert the JsonDoc into QString
    QJsonDocument jsonDoc(jsonObj);
    QByteArray jsonB = jsonDoc.toJson();
    QString jsonQStr = QString(jsonB);

    return jsonQStr;
}

// Serializer the information to SELECT in JSON format
QString JsonSerializer::updateJSON(Lista<QString> *slotsList,
                                   Lista<QString> *valuesList,
                                   Lista<QString> *whereList,
                                   Lista<QString> *whereValuesList){
    // Step 1: Creates three JsonArray w/ the Data of the list
    // ADVICE: Each pair of lists (slot-values and where-values) must have the same size!!

    QJsonArray slot;
    QJsonArray slotValues;
    QJsonArray where;
    QJsonArray whereValues;

    for(int i = 0; i < slotsList->size();i++){
        slot.append(slotsList->get_index(i));
        slotValues.append(valuesList->get_index(i));
    }

    for(int i = 0; i < whereList->size();i++){
        where.append(whereList->get_index(i));
        whereValues.append(whereValuesList->get_index(i));
    }

    // Step 2: Add those JsonArray  and create a JSON Object
    QJsonObject jsonObj;
    jsonObj.insert("slots",slot);
    jsonObj.insert("slotsValues",slotValues);
    jsonObj.insert("where",where);
    jsonObj.insert("whereValues",whereValues);

    // Step 3: Convert the JsonDoc into QString
    QJsonDocument jsonDoc(jsonObj);
    QByteArray jsonB = jsonDoc.toJson();
    QString jsonQStr = QString(jsonB);

    return jsonQStr;
}

// Serializer the information to DELETE in JSON format
QString JsonSerializer::deleteJSON(Lista<QString> *whereList,
                                   Lista<QString> *whereValuesList){
    // Step 1: Creates three JsonArray w/ the Data of the list
    // ADVICE: Both lists (where and whereValues) must have the same size!!

    QJsonArray where;
    QJsonArray whereValues;

    for(int i = 0; i < whereList->size();i++){
        where.append(whereList->get_index(i));
        whereValues.append(whereValuesList->get_index(i));
    }

    // Step 2: Add those JsonArray  and create a JSON Object
    QJsonObject jsonObj;
    jsonObj.insert("where",where);
    jsonObj.insert("whereValues",whereValues);

    // Step 3: Convert the JsonDoc into QString
    QJsonDocument jsonDoc(jsonObj);
    QByteArray jsonB = jsonDoc.toJson();
    QString jsonQStr = QString(jsonB);

    return jsonQStr;
}

