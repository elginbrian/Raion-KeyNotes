package com.raion.keynotes.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raion.keynotes.screen.HomeScreen
import com.raion.keynotes.screen.NoteScreen
import com.raion.keynotes.screen.RaionAPIViewModel

@Composable
fun NavHost(
    viewModel: RaionAPIViewModel
){
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = NavEnum.HomeScreen.name
    ) {
        composable(NavEnum.HomeScreen.name){
            HomeScreen(
                viewModel = viewModel,
                navController = navController,
                addNote = { viewModel.addNote(it.first, it.second) },
                deleteNote = { viewModel.deleteNote(it)}
            )
        }

        composable(
            NavEnum.NoteScreen.name+"/{noteId}",
            arguments = listOf(navArgument(name = "noteId"){type = NavType.StringType})
        ){
            backStackEntry ->
            NoteScreen(
                navController = navController, backStackEntry.arguments?.getString("noteId"),
                viewModel = viewModel
            )
        }
    }
}