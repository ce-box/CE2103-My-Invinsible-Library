#ifndef LECTORSINTAXIS_H
#define LECTORSINTAXIS_H

#include <string>
#include <vector>
#include <boost/algorithm/string.hpp>
#include <iostream>
#include <qdebug.h>

class LectorSintaxis
{
private:
    std::string inputIDE;
    int inputSize;
    int idError;

public:
    LectorSintaxis(std::string inputIDE);
    std::string obtenerInstruccion();
    std::string manejarInputIDE();
    std::string manejarInstruccionInsert();
    std::string obtenerContenidoTupla();
    std::string obtenerColumnasInsert();
    std::string obtenerValoresInsert();
};

#endif // LECTORSINTAXIS_H
