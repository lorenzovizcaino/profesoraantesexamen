package com.antonio.profesoraantesexamen.ui.model

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import java.io.File
import java.io.ObjectInputStream

data class Item(val nombre: String, val descr: String)

val items = mutableStateListOf<Item>()//usando .txt

val itemsSer = mutableStateListOf<ItemSer>()//usando .dat

fun grabarCambiosFich(context: Context) {
    val file = File(context.filesDir, "items.txt")
    file.delete()
    for (item in items) {
        file.appendText("${item.nombre}\n${item.descr}\n")
    }
}

fun leerDatosFich(context: Context) {
    val file = File(context.filesDir, "items.txt")
    if (file.exists()) {
        //fichero en AVD en ubicación-> /data/user/0/com.example.NombreApp/files/items.txt->a ver en Divice Explorer
//        Toast.makeText(context,"el fichero está en->"+file.path, Toast.LENGTH_SHORT).show()
        val lista = file.readLines()
        var indice = 0
        while (indice < lista.size) {
            val itemNew = Item(lista.get(indice), lista.get(indice + 1))
            val itemNewSer = ItemSer(lista.get(indice), lista.get(indice + 1))

            if (!items.contains(itemNew)) {
                items.add(itemNew)

                itemsSer.add(itemNewSer)
            }
            indice += 2
        }
    }
    else {
        Toast.makeText(context,"el fichero NO existe->"+file.path, Toast.LENGTH_SHORT).show()
    }
}

fun leerDatos_Fichdat(context: Context){
    val archivo = File(context.filesDir, "items.dat")

    val input = ObjectInputStream(archivo.inputStream())

    input.use {
        // Podemos leer cualquier objeto serializable, ya sea un objeto o una colección
//        val itemsLeidos = it.readObject() as List<ItemSer>
//        itemsSer.addAll(itemsLeidos)
        // podría leer uno a uno
        while (it.available() > 0) {
            val item = it.readObject() as ItemSer
            itemsSer.add(item)
        }
    }
}

fun grabarCambios_Fichdat(context: Context) {
    val archivo = File(context.filesDir, "items.dat")
    archivo.delete()
    for (item in itemsSer) {
        // Serializar objeto
        serializarObjetoFich(item, archivo)
    }
}

