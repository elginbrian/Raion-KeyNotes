package com.raion.keynotes.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.screen.DownloadScreen
import com.raion.keynotes.screen.HomeScreen
import com.raion.keynotes.screen.LoginScreen
import com.raion.keynotes.screen.NoteDAOViewModel
import com.raion.keynotes.screen.CreateNewNoteScreen
import com.raion.keynotes.screen.NoteScreen
import com.raion.keynotes.screen.ProfileScreen
import com.raion.keynotes.screen.RaionAPIViewModel


@Composable
fun NavHost(
    RaionAPIViewModel: RaionAPIViewModel,
    NoteDAOViewModel: NoteDAOViewModel
){
    val navController  = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    var noteList  = NoteDAOViewModel.notelist.collectAsState().value
    //var tokenList = RaionAPIViewModel.tokenlist.collectAsState().value

    //if(!tokenList.isNullOrEmpty()){
    //    var currentToken = tokenList.last().tokenString
    //}

    androidx.navigation.compose.NavHost(
        navController = navController,
        startDestination = NavEnum.LoginScreen.name
    ) {
        composable(
            NavEnum.LoginScreen.name, enterTransition = {
                return@composable fadeIn(tween(1000))
            }, exitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popEnterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ){
            LoginScreen(
                navController = navController,
                viewModel = RaionAPIViewModel,
                postRegister = { RaionAPIViewModel.postRegister(it[0], it[1], it[2], it[3]) },
                postLogin = { RaionAPIViewModel.postLogin(it[0], it[1]) }

            )
        }

        composable(
            NavEnum.HomeScreen.name, enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ){
            HomeScreen(
                viewModel = RaionAPIViewModel,
                navController = navController,
                postNote = { RaionAPIViewModel.postNote(it.first, it.second) },
                deleteNote = { RaionAPIViewModel.deleteNote(it)}
            )
        }

        composable(
            NavEnum.NoteScreen.name+"/{noteId}",
            arguments = listOf(navArgument(name = "noteId"){type = NavType.StringType}),
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ){
            backStackEntry ->
            NoteScreen(
                navController = navController, backStackEntry.arguments?.getString("noteId"),
                viewModel = RaionAPIViewModel,
                deleteNote = { RaionAPIViewModel.deleteNote(it)},
                downloadNote = { NoteDAOViewModel.addNote(NoteClass(
                    noteId = it[0],
                    title = it[1],
                    description = it[2],
                    createdAt = it[3],
                    updatedAt = it[4]))
                },
                putNote = { RaionAPIViewModel.putNote(it[0], it[1], it[2]) }
            )
        }

        composable(
            NavEnum.CreateNewNoteScreen.name,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ){
            CreateNewNoteScreen(
                navController = navController,
                viewModel = RaionAPIViewModel,
                postNote = { RaionAPIViewModel.postNote(it.first, it.second) }
            )
        }

        composable(
            NavEnum.ProfileScreen.name,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ){
            ProfileScreen(
                navController = navController,
                viewModel = RaionAPIViewModel,
                putUserDetail = { RaionAPIViewModel.putUserDetail(it[0], it[1], it[2]) }
            )
        }

        composable(
            NavEnum.DownloadScreen.name,
            enterTransition = {
                return@composable slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Start, tween(700)
                )
            }, popExitTransition = {
                return@composable slideOutOfContainer(
                    AnimatedContentTransitionScope.SlideDirection.End, tween(700)
                )
            }
        ){
            DownloadScreen(
                noteDAOViewModel = NoteDAOViewModel,
                navController = navController,
                deleteNote = { NoteDAOViewModel.removeNote(it)},
                noteList = noteList,
                RaionAPIViewModel = RaionAPIViewModel
            )
        }
    }
}