#ifndef NODO_H
#define NODO_H

// Bibliotecas
#include <iostream>

using namespace std;

//! @abstract Nodo de tipo generico para una Lista enlazada
template <class T>
class Nodo
{
    T _dato; //!< Dato T que almacena el nodo
    Nodo* _next; //!< Puntero al sgte nodo de la lista

public:

    //! @brief Constructor
    //! @param [in]dato: Dato que almacena el nodo
    Nodo(T dato);

    //! @brief Asigna un valor al dato que almacena el nodo
    //! @param dato: Dato que se almacenara
    void set_Dato(T dato);

    //! @brief Asigna el sgte nodo
    //! @param next: Nodo que sera colocado como sgte
    void set_Next(Nodo* next);

    //! @brief Retorna el dato almacenado en el nodo
    T get_Dato();

    //! @brief Retorna el sgte nodo de este nodo
    Nodo* get_Next();

    //! @brief Imprime la informacion del nodo en consola
    void print_Nodo();
};

#include "nodo_def.h"

#endif // NODO_H
