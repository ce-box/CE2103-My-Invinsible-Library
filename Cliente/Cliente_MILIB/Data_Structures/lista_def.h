#ifndef LISTA_DEF_H
#define LISTA_DEF_H

/* -------------------------------
 *          CONSTRUCTOR
 * -------------------------------*/

template <class T>
Lista<T>::Lista(){
    this->front = nullptr;
    this->_size = 0;
}

/* -------------------------------
 *          METODOS LISTA
 * -------------------------------*/

// Insertar elemento al final de la lista
template <class T>
void Lista<T>::push_back(T dato){
    if(!front){
        this->front = new Nodo<T>(dato);
        this->_size++;
    } else {

        Nodo<T> *aux = front;
        while(aux->get_Next()!= nullptr){
            aux = aux->get_Next();
        }

        aux->set_Next(new Nodo<T>(dato));
        this->_size++;

    }
}


// Insertar elemento al inicio de la lista
template <class T>
void Lista<T>::push_front(T dato){

    Nodo<T> *new_Node = new Nodo<T>(dato);
    if(!front){
        this->front = new_Node;
        this->_size++;
    } else {
        new_Node->set_Next(front);
        this->front = new_Node;
        this->_size++;
    }
}


// Elimina elemento al final de la lista
template <class T>
T Lista<T>::pop_back(){
    if(front){
        Nodo<T> *aux = front,*prev = nullptr;

        while(aux->get_Next()!= nullptr){
            prev = aux;
            aux = aux->get_Next();
        }

        T ans;
        if(prev){
            prev->set_Next(nullptr);
            ans = aux->get_Dato();

            delete(aux);
            this->_size--;

        } else {
            ans = pop_front();
        }
        return ans;
    }
}


// Elimina elemento al inicio de la lista
template <class T>
T Lista<T>::pop_front(){
    if(front){
        Nodo<T> *aux = front;
        front = front->get_Next();
        aux->set_Next(nullptr);

        T ans = aux->get_Dato();

        delete(aux);
        this->_size--;
        return ans;
    }
}

// Inserta un elemento en el indice dado
template <class T>
void Lista<T>::insert(int index,T dato){
    if(index < _size){
        Nodo<T> *prev_Nodo = front,*next_Nodo,*new_Nodo = new Nodo<T>(dato);

        for(int i = 0;i!= index-1;i++){
            prev_Nodo = prev_Nodo->get_Next();
        }

        next_Nodo = prev_Nodo->get_Next();
        prev_Nodo->set_Next(new_Nodo);
        new_Nodo->set_Next(next_Nodo);
        this->_size++;
    }
}

// Elimina un elemento de la lista, dado el dato
template <class T>
T Lista<T>::remove(T dato){

    Nodo<T> *aux = front, *prev = nullptr;
    T ans;
    while(aux && aux->get_Dato() != dato){
        prev = aux;
        aux = aux->get_Next();
    }

    if(aux){
        ans = remove_swapping(prev,aux);
    }
    return ans;
}

// Elimina un elemento de la lista dado su indice
template <class T>
T Lista<T>::remove_at(int index){
    Nodo<T> *aux = front, *prev = nullptr;

    for(int i = 0; i != index;i++){
        prev = aux;
        aux = aux->get_Next();
    }

    return remove_swapping(prev,aux);
}

template <class T>
T Lista<T>::remove_swapping(Nodo<T> *prev,Nodo<T> *aux){
    T ans;
    if(!prev){
        ans = pop_front();
    } else{
        prev->set_Next(aux->get_Next());
        ans = aux->get_Dato();
    }
    this->_size--;
    return ans;
}

/* -------------------------------
 *        SETTERS & GETTERS
 * -------------------------------*/

template <class T>
T Lista<T>::get_index(int index){
    Nodo<T> *aux = front;
    for(int i = 0;i!= index;i++){
        aux = aux->get_Next();
    }
    return aux->get_Dato();
}

template <class T>
T Lista<T>::get(T dato){
    Nodo<T> *aux = front;
    for(int i = 0;i < _size;i++){
        if(aux->get_Dato() == dato)
            break;
        aux = aux->get_Next();
    }

    return aux->get_Dato();
}

template <class T>
int Lista<T>::size(){
    return this->_size;
}

template <class T>
bool Lista<T>::contains(T elem){
    if(front){
        for(Nodo<T> *nodo = front; nodo!= nullptr; nodo = nodo->get_Next()){
            if(nodo->get_Dato() == elem)
                return true;
        }
    }
    return false;
}


/* -------------------------------
 *              UTIL
 * -------------------------------*/
template <class T>
void Lista<T>::print_lista(){
    for(Nodo<T> *nodo = front; nodo!= nullptr; nodo = nodo->get_Next()){
        cout<<"[ "<<nodo->get_Dato()<<" ]->";
    }
    cout<<"NULL"<<endl;
}

#endif // LISTA_DEF_H
