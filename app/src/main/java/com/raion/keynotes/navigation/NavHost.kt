package com.raion.keynotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.raion.keynotes.screen.HomeScreen
import com.raion.keynotes.screen.RaionAPIViewModel

@Composable
fun NavHost(
    viewModel: RaionAPIViewModel
){
    val navController = rememberNavController()
    //val note = viewModel.data.
    val coroutineScope = rememberCoroutineScope()

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = NavEnum.HomeScreen.name
    ) {
        composable(NavEnum.HomeScreen.name){
            HomeScreen(
                viewModel = viewModel
            )
        }
    }
}