package com.antonio.profesoraantesexamen.ui.model

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

data class ItemSer(val nombre: String, val descr: String) : Serializable

fun serializarObjetoFich(objeto: Any, archivo: File) {
    ObjectOutputStream(FileOutputStream(archivo)).use { it.writeObject(objeto) }
}

fun deserializarObjetoFich(archivo: File): Any? {
    return ObjectInputStream(FileInputStream(archivo)).use { it.readObject() }
}
