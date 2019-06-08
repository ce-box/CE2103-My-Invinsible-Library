#ifndef LISTA_HPP
#define LISTA_HPP

//Bibliotecas
#include <iostream>
#include "nodo.hpp"

template <class T>
//! \brief The Lista class
class Lista
{
    Nodo<T> *front; //!< Primer nodo de la lista
    int _size; //!< Tamaño de la lista

    //! \brief remove_swapping
    //! \param prev
    //! \param aux
    T remove_swapping(Nodo<T> *prev, Nodo<T> *aux);


public:

    //! \brief Lista
    Lista();

    //! \brief push_back
    //! \param dato
    void push_back(T dato);

    //! \brief push_front
    //! \param dato
    void push_front(T dato);

    //! \brief pop_back
    //! \return
    T pop_back();

    //! \brief pop_front
    //! \return
    T pop_front();

    //! \brief get
    //! \param dato
    //! \return
    T get(T dato);

    //! \brief get_index
    //! \param index
    //! \return
    T get_index(int index);

    //! \brief insert
    //! \param index
    //! \param dato
    void insert(int index, T dato);

    //! \brief remove
    //! \param dato
    //! \returns
    T remove(T dato);

    //! \brief remove_at
    //! \param index
    //! \return
    T remove_at(int index);

    //! \brief size
    //! \return
    int size();

    //! \brief print_lista
    void print_lista();

    //! \brief empty
    //! \return
    bool empty(){
        return !front;
    }

    //! \brief contains
    //! \param elem
    //! \return
    bool contains(T elem);

};

#include "lista_def.h"
#endif // LISTA_HPP
