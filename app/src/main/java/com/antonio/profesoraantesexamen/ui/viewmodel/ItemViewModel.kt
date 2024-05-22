package com.antonio.profesoraantesexamen.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.antonio.profesoraantesexamen.ui.model.ItemSer
import com.antonio.profesoraantesexamen.ui.model.getListaclass

class ItemViewModel:ViewModel(){



    fun getLista():MutableList<ItemSer>{
        return  getListaclass()
    }

    var nombre by mutableStateOf("")
        private set

    var descr by mutableStateOf("")
        private set

    var objeto by mutableStateOf(ItemSer("",""))
        private set

    fun set_nombre(nombre:String){
        this.nombre=nombre
    }

    fun set_descr(descr:String){
        this.descr=descr
    }

    fun set_Objeto(objeto:ItemSer){
        this.objeto=objeto
    }




}