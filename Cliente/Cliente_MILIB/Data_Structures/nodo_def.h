#ifndef NODO_DEF_H
#define NODO_DEF_H

/* -------------------------------
 *          CONSTRUCTOR
 * -------------------------------*/

template <class T>
Nodo<T>::Nodo(T dato)
{
    this->_dato = dato;
    this->_next = nullptr;
}

/* -------------------------------
 *          SETTER & GETTERS
 * -------------------------------*/

template <class T>
void Nodo<T>::set_Dato(T dato){
    this->_dato = dato;
}

template <class T>
void Nodo<T>::set_Next(Nodo* next){
    this->_next = next;
}

template <class T>
T Nodo<T>::get_Dato(){
    return this->_dato;
}

template <class T>
Nodo<T>* Nodo<T>::get_Next(){
    return this->_next;
}

/* -------------------------------
 *          METODOS UTIL
 * -------------------------------*/

template <class T>
void Nodo<T>::print_Nodo(){
    cout<<"{ Dato: "+ to_string(this->_dato)+"}"<<endl;
}

#endif // NODO_DEF_H
