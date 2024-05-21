package com.antonio.profesoraantesexamen.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.antonio.profesoraantesexamen.ui.model.Item
import com.antonio.profesoraantesexamen.ui.model.ItemSer
import com.antonio.profesoraantesexamen.ui.model.grabarCambiosFich
import com.antonio.profesoraantesexamen.ui.model.grabarCambios_Fichdat
import com.antonio.profesoraantesexamen.ui.model.items
import com.antonio.profesoraantesexamen.ui.model.itemsSer
import com.antonio.profesoraantesexamen.ui.model.leerDatosFich
import com.antonio.profesoraantesexamen.ui.model.leerDatos_Fichdat
import com.antonio.profesoraantesexamen.ui.model.serializarObjetoFich
import java.io.File

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreenAppFich() {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Items a Fichero .tx y .dat") })
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(text = "Listado-numEltos-")
                Text(text = items.size.toString())
            }
        }
    ) {// (1)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it) // (3)
            ,
            verticalArrangement = Arrangement.Top
        ) {

            AdministrarItems()
        }
    }
}

@Preview(showSystemUi = false)
@Composable
fun AppConFichsScreen() {

    ScaffoldScreenAppFich()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrarItems() {
    val activity = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var descr by remember { mutableStateOf("") }

    Column(

    ) {
        OutlinedTextField(value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre de item") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        OutlinedTextField(value = descr,
            onValueChange = { descr = it },
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Button(onClick = {
            val nuevoItem = Item(nombre, descr)
            items.add(nuevoItem)

            val nuevoItemSer = ItemSer(nombre, descr)
            itemsSer.add(nuevoItemSer)

            val path = activity.getFilesDir()

            //.txt
            val file = File(path, "items.txt")
            file.appendText("${nombre}\n${descr}\n")

            //.dat
            val archivo = File(path,"items.dat")
            // Serializar objeto
            serializarObjetoFich(nuevoItemSer, archivo)

            nombre = ""
            descr = ""
        }, modifier = Modifier.padding(5.dp)) {
            Text(text = "Agregar", /*modifier = Modifier.fillMaxWidth()*/)
        }

        val file = File(activity.filesDir, "items.txt")
        if (file.exists()) {

            leerDatosFich(activity)//uso de archivos .txt,.dat para persistir datos
            leerDatos_Fichdat(activity)//uso .dat

            LazyColumn() {
                itemsIndexed(items) { indice, item ->
                    MostrarItem(
                        indice,
                        item,
                        editNombre = { nombre = it },
                        editDescr = { descr = it })
                }
            }
        }
    }
}

@Composable
fun MostrarItem(indice: Int, item: Item,editNombre:(String)->Unit,editDescr:(String)->Unit) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,//hace que ocupen todo el espacio horizontal
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(

        ){
            Text(text = "Nombre-> "+item.nombre)
            Text(text = "Descr->"+item.descr)
        }
        Column(

        ) {
            Image(painter = painterResource(id = android.R.drawable.ic_delete),
                contentDescription = "",
                modifier = Modifier.clickable {
                    items.removeAt(indice)

                    itemsSer.removeAt(indice)

                    grabarCambiosFich(context)//usando .txt
                    grabarCambios_Fichdat(context)
                })
        }
        Column(

        ){
            Image(painter = painterResource(id = android.R.drawable.ic_menu_edit),
                contentDescription = "",
                modifier = Modifier.clickable {

                    Toast.makeText(context, "Modifica Datos Contacto seleccionado.", Toast.LENGTH_SHORT).show()

                    items.removeAt(indice)
                    itemsSer.removeAt(indice)

                    grabarCambiosFich(context)//usando .txt
                    grabarCambios_Fichdat(context)//usando .dat

                    editNombre(item.nombre)
                    editDescr(item.descr)

                })
        }

    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .width(4.dp), color = Color.Black
    )
}

