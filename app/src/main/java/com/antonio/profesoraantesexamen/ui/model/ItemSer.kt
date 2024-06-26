package com.antonio.profesoraantesexamen.ui.model

import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import java.io.EOFException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

data class ItemSer(val nombre: String, val descr: String) : Serializable
    var itemsSer = mutableStateListOf<ItemSer>(
//        ItemSer("Casa", "Morada"),
//        ItemSer("Camion", "Vehiculo"),
    )
        private set

    val nombreArchivo="objeto6.dat"

    fun getListaclass(): MutableList<ItemSer> {
        return itemsSer
    }




//DESDE AQUI PARA GUARDAR EN FICHERO

fun guardarListaEnFichero(context: Context) {
    var archivo = File(context.filesDir, nombreArchivo)
    if(!archivo.exists()){
        val objectOutputStream = ObjectOutputStream(FileOutputStream(archivo))
        var contador = 0
        itemsSer.forEach { item ->
            //contador++

            var itemSer = ItemSer(
                item.nombre,item.descr
            )

            // Serializar objeto
            serializarObjeto(itemSer, objectOutputStream)
        }
        objectOutputStream.close()
    }
    else{
        leerItemArchivo(context)
    }

}

fun guardarItemEnFichero(context: Context,itemSer: ItemSer){
    try{
        var archivo = File(context.filesDir, nombreArchivo)

        val objectOutputStream = object : ObjectOutputStream(FileOutputStream(archivo,true)) {
            override fun writeStreamHeader() {}  //para no sobreescribir la cabecera del archivo
        }
        serializarObjeto(itemSer, objectOutputStream)
        objectOutputStream.close()
        println("Objeto agregado correctamente al archivo.")
    } catch (ex: IOException) {
        println("Error al escribir el objeto en el archivo: ${ex.message}")
    }

}

fun escribirFichero(context: Context){
    var archivo = File(context.filesDir, nombreArchivo)
    val objectOutputStream = ObjectOutputStream(FileOutputStream(archivo))
    itemsSer.forEach(){item->
        serializarObjeto(item, objectOutputStream)
    }
    objectOutputStream.close()
}

fun serializarObjeto(objeto: ItemSer, objectOutputStream: ObjectOutputStream) {
    objectOutputStream.writeObject(objeto)
}

fun leerItemArchivo(context: Context) {
    var archivo = File(context.filesDir, nombreArchivo)
    itemsSer.clear()
    deserializarObjeto(archivo)
    itemsSer.sortBy { it.nombre }

}

fun deserializarObjeto(archivo: File) {
    try {
        val objectInputStream = ObjectInputStream(FileInputStream(archivo))

        while (true) {
            try {
                val itemSer = objectInputStream.readObject()
                if (itemSer is ItemSer) {
                    itemsSer.add(itemSer)

                } else {
                    break;
                }

            } catch (ex: EOFException) {
                break
            }
        }

        objectInputStream.close()
    } catch (ex: IOException) {
        println("Error al leer el archivo: ${ex.message}")
    } catch (ex: ClassNotFoundException) {
        println("Clase no encontrada: ${ex.message}")
    }


}

//HASTA AQUI PARA GUARDAR EN FICHERO




