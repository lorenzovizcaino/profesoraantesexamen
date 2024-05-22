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
import androidx.compose.foundation.lazy.items

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.antonio.profesoraantesexamen.ui.model.ItemSer
import com.antonio.profesoraantesexamen.ui.model.escribirFichero
//import com.antonio.profesoraantesexamen.ui.model.grabarCambios_Fichdat
import com.antonio.profesoraantesexamen.ui.model.guardarItemEnFichero
import com.antonio.profesoraantesexamen.ui.model.guardarListaEnFichero
//import com.antonio.profesoraantesexamen.ui.model.leerDatos_Fichdat
//import com.antonio.profesoraantesexamen.ui.model.serializarObjetoFich
import com.antonio.profesoraantesexamen.ui.viewmodel.ItemViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreenAppFich(navController: NavController, viewModel: ItemViewModel) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = { Text(text = "Items a Fichero .dat") })
        },
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.primary,
            ) {
                Text(text = "Listado-numEltos-")
                Text(text = viewModel.getLista().size.toString())
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

            AdministrarItems(navController,viewModel)
        }
    }
}


@Composable
fun Menu(navController: NavController, viewModel: ItemViewModel) {

    ScaffoldScreenAppFich(navController,viewModel)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdministrarItems(navController: NavController, viewModel: ItemViewModel) {
    val activity = LocalContext.current

    if(viewModel.banderaFichero){
        guardarListaEnFichero(activity)
        print("HOLA")
        viewModel.set_banderaFichero(false)
    }




    Column(

    ) {
        OutlinedTextField(value = viewModel.nombre,
            onValueChange = { viewModel.set_nombre(it)},
            label = { Text("Nombre de item") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        OutlinedTextField(value = viewModel.descr,
            onValueChange = { viewModel.set_descr(it) },
            label = { Text("Descripción") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        )
        Button(onClick = {


            val nuevoItemSer = ItemSer(viewModel.nombre, viewModel.descr)
            viewModel.getLista().add(nuevoItemSer)

            val path = activity.getFilesDir()


            guardarItemEnFichero(activity,nuevoItemSer)

            viewModel.set_nombre("")
            viewModel.set_descr("")
        }, modifier = Modifier.padding(5.dp)) {
            Text(text = "Agregar", /*modifier = Modifier.fillMaxWidth()*/)
        }




            LazyColumn(){
                items(viewModel.getLista()){
                    MostrarItem(
                        viewModel=viewModel,
                        objeto=it ,
                        editNombre = { viewModel.set_nombre(it) },
                        editDescr = { viewModel.set_descr(it) }
                    )
                }
            }

    }
}



@Composable
fun MostrarItem(
    objeto: ItemSer,
    editNombre: (String) -> Unit,
    editDescr: (String) -> Unit,
    viewModel: ItemViewModel
) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Column(

        ){
            Text(text = "Nombre-> "+objeto.nombre)
            Text(text = "Descr->"+objeto.descr)
        }
        Column(

        ) {
            Image(painter = painterResource(id = android.R.drawable.ic_delete),
                contentDescription = "",
                modifier = Modifier.clickable {

                    viewModel.getLista().remove(objeto)

                    escribirFichero(context)
                })
        }
        Column(

        ){
            Image(painter = painterResource(id = android.R.drawable.ic_menu_edit),
                contentDescription = "",
                modifier = Modifier.clickable {

                    Toast.makeText(context, "Modifica Datos Contacto seleccionado.", Toast.LENGTH_SHORT).show()


                    viewModel.getLista().remove(objeto)

                    escribirFichero(context)

                    editNombre(objeto.nombre)
                    editDescr(objeto.descr)

                })
        }

    }
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .width(4.dp), color = Color.Black
    )

}



