#include "lectorsintaxis.h"
using namespace std;

LectorSintaxis::LectorSintaxis(string inputIDE){
    this->inputIDE = inputIDE;
    this->inputSize = inputIDE.size();
    this->idError = 0;
}

string LectorSintaxis::manejarInputIDE(){
    string instruccion = obtenerInstruccion();
    vector<string> datosObtenidos;
    if(instruccion == "INSERT"){
        instruccion = "1-" + manejarInstruccionInsert();
        if(idError != 0)
            instruccion = to_string(idError + 4);
    }else if(instruccion == "SELECT"){
        instruccion = "2-" + manejarInstruccionSelect();
        if(idError != 0)
            instruccion = to_string(idError + 4);
    }else if(instruccion == "DELETE"){
        instruccion = manejarInstruccionDelete();
        if(idError != 0)
            instruccion = to_string(idError + 4);
        else{
            boost::split(datosObtenidos, instruccion, boost::is_any_of(","));
            string where;
            string valores;
            boost::split(datosObtenidos, instruccion, boost::is_any_of("="));
            for(int i = 0; i < datosObtenidos.size(); i += 2){
                where += datosObtenidos[i] + ",";
                valores += datosObtenidos[i+1] + ",";
            }
            where = where.substr(0, where.size()-1);
            valores = valores.substr(0, valores.size()-1);
            instruccion = "3-" + where + "-" + valores;
        }
    }else if(instruccion == "UPDATE"){
        instruccion = manejarInstruccionUpdate();
        if(idError != 0)
            instruccion = to_string(idError + 4);
        else{
            boost::split(datosObtenidos, instruccion, boost::is_any_of("-"));
            string set = datosObtenidos[0];
            string where = datosObtenidos[1];
            datosObtenidos.clear();
            boost::split(datosObtenidos, set, boost::is_any_of(","));
            vector<string> varSet;
            string columnas;
            string valores;
            for(int i = 0; i < datosObtenidos.size(); i++){
                boost::split(varSet, datosObtenidos[i], boost::is_any_of("="));
                columnas += varSet[0] + ",";
                valores += varSet[1] + ",";
            }
            columnas = columnas.substr(0, columnas.size()-1);
            valores = valores.substr(0, valores.size()-1);
            datosObtenidos.clear();
            boost::split(datosObtenidos, where, boost::is_any_of("="));
            string variableWhere = datosObtenidos[0];
            string valorWhere = datosObtenidos[1];
            instruccion = "4-" + columnas + "-" + valores + "-" + variableWhere + "-" + valorWhere;
        }
    }else{
        //ERROR: NO SE ENCUENTRA INSTRUCCION SQL
        idError = 1;
        instruccion = to_string(idError + 4);
    }
    return instruccion;
}

string LectorSintaxis::obtenerInstruccion(){
    string instruccion;
    string caracterActual;
    for(int posicion = 0; posicion < inputSize; posicion++){
        caracterActual = inputIDE[posicion];
        if(caracterActual != " ")
            instruccion += caracterActual;
        else{
            inputIDE = inputIDE.substr(posicion+1, inputSize-posicion);
            inputSize = inputIDE.size();
            return instruccion;
        }
    }
}

string LectorSintaxis::manejarInstruccionInsert(){
    string verificarSintaxis = inputIDE.substr(0, 13);
    if(verificarSintaxis != "INTO METADATA"){ //ERROR: NO SE ENCUENTRA SINTAXIS "INTO METADATA"
        idError = 2;
        return "ERROR";
    }
    inputIDE = inputIDE.substr(13, inputIDE.size()-13);
    inputSize = inputIDE.size();
    string columnas = obtenerColumnasInsert();
    string valores = obtenerValoresInsert();
    return columnas + "-" + valores;
}

string LectorSintaxis::obtenerContenidoTupla(){
    string contenido;
    string caracterActual;
    bool enString = false;
    for(int posicion = 0; posicion < inputSize; posicion++){
        caracterActual = inputIDE[posicion];
        if(caracterActual != ")"){
            if(caracterActual == "\"") enString = !enString;
            else if(enString) contenido += caracterActual;
            else if(caracterActual != " ") contenido += caracterActual;
        }else{
            inputIDE = inputIDE.substr(posicion+1, inputSize-posicion);
            inputSize = inputIDE.size();
            if(enString){
                idError = 14;
                return "ERROR";
            }
            return contenido;
        }
    }
    return "ERROR";
}


string LectorSintaxis::obtenerColumnasInsert(){
    string caracterActual;
    for(int posicion = 0; posicion < inputSize; posicion++){
        caracterActual = inputIDE[posicion];
        if(caracterActual != " "){
            if(caracterActual == "("){
                inputIDE = inputIDE.substr(posicion+1, inputSize-posicion);
                inputSize = inputIDE.size();
                break;
            }else{
                idError = 3; //ERROR: NO SE PUEDE ENCONTRAR NOMBRES DE COLUMNAS EN INSERT
                return "ERROR";
            }
        }
    }

    string nombreColumnas = obtenerContenidoTupla();
    if(nombreColumnas == "ERROR") //ERROR: CERRAR PARENTESIS INSERT
        idError = 4;
    return nombreColumnas;
}

string LectorSintaxis::obtenerValoresInsert(){
    string caracterActual;
    string verificarSintaxis;
    for(int posicion = 0; posicion < inputSize; posicion++){
        caracterActual = inputIDE[posicion];
        if(caracterActual != " " && caracterActual != "\n"){
            if(caracterActual == "("){
                inputIDE = inputIDE.substr(posicion+1, inputSize-posicion);
                inputSize = inputIDE.size();
                break;
            }else
                verificarSintaxis += caracterActual;
        }
    }

    if(verificarSintaxis != "VALUES"){
        idError = 5; //ERROR: NO SE ENCUENTRA SINTAXIS VALUES
        return "ERROR";
    }

    string valores = obtenerContenidoTupla();
    string finInstruccion;
    finInstruccion = inputIDE[0];
    if(valores == "ERROR")
        idError = 4;
    else if(finInstruccion != ";"){
        idError = 6; //ERROR: FALTA PUNTO Y COMA
        valores = "ERROR";
    }
    return valores;
}

string LectorSintaxis::manejarInstruccionSelect(){
    string columnas = obtenerColumnasSelect();
    if(idError == 0){
        if(inputIDE.substr(0, 13) != "FROM METADATA"){
            idError = 8; //NO SE ENCUENTRA SINTAXIS FROM METADATA
            return "ERROR";
        }
        inputIDE = inputIDE.substr(13, inputSize-13);
        inputSize = inputIDE.size();
        if(inputIDE == ";")
            return columnas;
        else if(inputIDE.substr(1, 6) == "WHERE "){
            inputIDE = inputIDE.substr(7, inputSize-7);
            inputSize = inputIDE.size();
            if(inputIDE.substr(inputIDE.size()-1, 1) != ";"){
                idError = 6; //ERROR: FALTA PUNTO Y COMA
                return "ERROR";
            }
            string condicional = obtenerCondicionales();
            qDebug()<<condicional.c_str();
            if(idError != 0) return "ERROR";
            bool isBetween = false;
            string columnaBetween;
            string verificarStr;
            if(condicional.size() >= 7){
                for(int i = 0; i < condicional.size()-7; i++){
                    verificarStr = condicional.substr(i, 7);
                    qDebug()<<"VerStr:"<<verificarStr.c_str();
                    if(verificarStr == "BETWEEN"){
                        isBetween = true;
                        condicional = condicional.substr(i+7, condicional.size());
                        break;
                    }else if(verificarStr.substr(0, 1) != " ")
                        columnaBetween += verificarStr.substr(0, 1);
                }
            }
            if(isBetween){
                string valoresBetween;
                for(int i = 0; i < valoresBetween.size()-3; i++){
                    if(condicional.substr(i, 3) == "AND"){
                        valoresBetween += "," + condicional.substr(i+3, condicional.size());
                        break;
                    }else valoresBetween = condicional.substr(i, 1);
                }
                return columnas + "-" + columnaBetween + "-" + valoresBetween;
            }
            string condFormato;
            for(int i = 0; i < condicional.size(); i ++){
                if(condicional.substr(i, 1) == "=")
                    condFormato += "-";
                else condFormato += condicional.substr(i, 1);
            }
            return columnas + "-" + condFormato;
        }else{
            idError = 9; //ERROR: WHERE NO ENCONTRADO
            return "ERROR";
        }
    }return "ERROR";
}

string LectorSintaxis::obtenerColumnasSelect(){
    string columnas;
    string caracterActual;
    for(int posicion = 0; posicion < inputSize; posicion++){
        caracterActual = inputIDE[posicion];
        if(caracterActual != " ")
            columnas += caracterActual;
        else if(columnas.substr(posicion-1, 1) != ","){
            inputIDE = inputIDE.substr(posicion+1, inputSize-posicion);
            inputSize = inputIDE.size();
            return columnas;
        }
    }
    idError = 7; //ERROR: NO SE ENCUENTRA COLUMNAS SELECT
    return "ERROR";
}

string LectorSintaxis::obtenerCondicionales(){
    string caracterActual;
    bool enString = false;
    string condicional;
    for(int posicion = 0; posicion < inputSize; posicion++){
        caracterActual = inputIDE[posicion];
        if(caracterActual != ";" && caracterActual != "\n"){
            if(caracterActual == "\"") enString = !enString;
            else if(enString) condicional += caracterActual;
            else if(caracterActual != " ") condicional += caracterActual;
        }else{
            inputIDE = inputIDE.substr(posicion+1, inputSize-posicion);
            inputSize = inputIDE.size();
            if(enString){
                idError = 14;
                return "ERROR";
            }
            return condicional;
        }
    }
    idError = 10; //ERROR: CONDICIONALES DE WHERE NO ENCONTRADOS
    return "ERROR";
}

string LectorSintaxis::manejarInstruccionDelete(){
    if(inputIDE.substr(0, 19) != "FROM METADATA WHERE"){ //ERROR: SINTAXIS FROM METADATA WHERE EN DELETE NO ENCONTRADO
        idError = 11;
        return "ERROR";
    }
    inputIDE = inputIDE.substr(20, inputSize-20);
    inputSize = inputIDE.size();
    if(inputIDE.substr(inputIDE.size()-1, 1) != ";"){
        idError = 6;
        return "ERROR";
    }
    string condicionales = obtenerCondicionales();
    if(idError != 0) return "ERROR";
    return condicionales;
}

string LectorSintaxis::manejarInstruccionUpdate(){
    if(inputIDE.substr(0, 13) != "METADATA\nSET "){ //ERROR: SINTAXIS DE UPDATE INCORRECTA
        idError = 12;
        return "ERROR1";
    }
    inputIDE = inputIDE.substr(13, inputSize-13);
    inputSize = inputIDE.size();

    string setStr = obtenerCondicionales();
    if(idError != 0) return "ERROR";

    if(inputIDE.substr(0, 6) != "WHERE "){
        idError = 9;
        return "ERROR";
    }

    inputIDE = inputIDE.substr(6, inputSize-6);
    inputSize = inputIDE.size();

    if(inputIDE.substr(inputIDE.size()-1, 1) != ";"){
        idError = 6;
        return "ERROR";
    }

    string condicionales = obtenerCondicionales();
    if(idError != 0) return "ERROR";

    return setStr + "-" + condicionales;
}
