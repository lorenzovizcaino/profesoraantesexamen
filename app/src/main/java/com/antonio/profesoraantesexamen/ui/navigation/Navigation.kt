package com.antonio.profesoraantesexamen.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.antonio.profesoraantesexamen.ui.screens.Menu


import com.antonio.profesoraantesexamen.ui.viewmodel.ItemViewModel

@Composable
fun Navigation() {
    val navController = rememberNavController()

    val viewModelObjeto=remember{ ItemViewModel() } //linea para recordar viewModel entre pantallas
    NavHost(navController, startDestination = Screens.Menu.route) {
        //pantalla principal con la navegación
        composable(route = Screens.Menu.route) {
            Menu(navController, viewModelObjeto) }//Nombre del fichero .kt al que navegar

//        composable(route = Screens.Inicio.route) {
//            Inicio(navController,viewModelProducto) //Nombre de la función composable a la que navegar
//        }
//        composable(route = Screens.ResumenCompras.route) {
//            ResumenCompras(navController,viewModelProducto) //Nombre de la función composable a la que navegar
//        }
//        composable(route = Screens.Registro.route) {
//            Registro(navController,viewModelLogin) //Nombre de la función composable a la que navegar
//        }
//        composable(route = Screens.ListaEmail.route) {
//            ListaEmail(navController,viewModelLogin) //Nombre de la función composable a la que navegar
//        }



    }
}






