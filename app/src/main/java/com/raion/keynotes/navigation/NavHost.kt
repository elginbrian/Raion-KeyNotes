package com.raion.keynotes.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.raion.keynotes.component.Notes
import com.raion.keynotes.component.UserDetail
import com.raion.keynotes.model.NoteClass
import com.raion.keynotes.model.TokenClass
import com.raion.keynotes.screen.DownloadScreen
import com.raion.keynotes.screen.HomeScreen
import com.raion.keynotes.screen.LoginScreen
import com.raion.keynotes.screen.NoteDAOViewModel
import com.raion.keynotes.screen.CreateNewNoteScreen
import com.raion.keynotes.screen.DownloadedNoteScreen
import com.raion.keynotes.screen.NoteScreen
import com.raion.keynotes.screen.ProfileScreen
import com.raion.keynotes.screen.RaionAPIViewModel
import com.raion.keynotes.util.processHoursSinceLogin
import com.raion.keynotes.util.processNoteList
import com.raion.keynotes.util.processUserDetail

@Composable
fun NavHost(
    RaionAPIViewModel: RaionAPIViewModel,
    NoteDAOViewModel: NoteDAOViewModel
){
    val navController          = rememberNavController()
    val coroutineScope         = rememberCoroutineScope()

    var downloadedNoteList     = NoteDAOViewModel.notelist.collectAsState().value
    var isToken: TokenClass?   = RaionAPIViewModel.token.collectAsState().value

    var token: TokenClass      = isToken ?: TokenClass("","2006-04-22T12:00:00.000000")
    var tokenTimestamp         = token.timeStamp
    var hoursSinceLastLogin    = processHoursSinceLogin(tokenTimestamp)

    var noteRawList            = RaionAPIViewModel.getNote.value.data
    var noteList               = processNoteList(noteRawList)

    var userDetail             = RaionAPIViewModel.getUserDetail.value.data
    var listOfUserDetail       = processUserDetail(userDetail)

    var notesLoadingValue      = false
    var userDetailLoadingValue = false

    var getAPIData             = remember { mutableStateOf(false) }
    if(getAPIData.value){
        Notes(viewModel = RaionAPIViewModel){ notesLoadingValue = it }
        UserDetail(viewModel = RaionAPIViewModel){ userDetailLoadingValue = it }
        getAPIData.value = false
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Color(30,30,30, 255)) {
        androidx.navigation.compose.NavHost(
            navController = navController,
            startDestination =
            if(hoursSinceLastLogin <= 23){
                NavEnum.HomeScreen.name
            } else {
                NavEnum.LoginScreen.name
            }
        ) {
            composable(
                NavEnum.LoginScreen.name, enterTransition = {
                    return@composable fadeIn(tween(700))
                }, exitTransition = {
                    return@composable fadeOut(tween(700))
                }, popEnterTransition = {
                    return@composable fadeIn(tween(700))
                }
            ){
                LoginScreen(
                    navController = navController,
                    postRegister = { RaionAPIViewModel.postRegister(it[0], it[1], it[2], it[3]) },
                    postLogin = { RaionAPIViewModel.postLogin(it[0], it[1]) }
                )

            }

            composable(
                NavEnum.HomeScreen.name, enterTransition = {
                    return@composable fadeIn(tween(700))
                }, popExitTransition = {
                    return@composable fadeOut(tween(700))
                }
            ){
                HomeScreen(
                    navController = navController,
                    noteList = noteList,
                    userDetailList = listOfUserDetail,
                    notesLoadingValue = notesLoadingValue,
                    userDetailLoadingValue = userDetailLoadingValue,
                    getAPIData = { getAPIData.value = it }
                )
            }

            composable(
                NavEnum.NoteScreen.name+"/{noteId}",
                arguments = listOf(navArgument(name = "noteId"){type = NavType.StringType}),
                enterTransition = {
                    return@composable fadeIn(tween(700))
                }, popExitTransition = {
                    return@composable fadeOut(tween(700))
                }
            ){
                    backStackEntry ->
                NoteScreen(
                    navController = navController, backStackEntry.arguments?.getString("noteId"),
                    noteList = noteList,
                    deleteNote = { RaionAPIViewModel.deleteNote(it)},
                    downloadNote = { NoteDAOViewModel.addNote(NoteClass(
                        noteId = it[0],
                        title = it[1],
                        description = it[2],
                        createdAt = it[3],
                        updatedAt = it[4]))
                    },
                    putNote = { RaionAPIViewModel.putNote(noteId = it[0], title =  it[1], description = it[2]) },
                    postNote = { RaionAPIViewModel.postNote(title = it[0], description = it[1])}
                )
            }

            composable(
                NavEnum.DownloadedNoteScreen.name+"/{noteId}",
                arguments = listOf(navArgument(name = "noteId"){type = NavType.StringType}),
                enterTransition = {
                    return@composable fadeIn(tween(700))
                }, popExitTransition = {
                    return@composable fadeOut(tween(700))
                }
            ){
                    backStackEntry ->
                DownloadedNoteScreen(
                    navController = navController, backStackEntry.arguments?.getString("noteId"),
                    downloadedNoteList = downloadedNoteList,
                    deleteNote = { NoteDAOViewModel.removeNote(it) },
                    putNote = { NoteDAOViewModel.updateNote(noteId = it[0], title = it[1], description = it[2]) }
                )
            }

            composable(
                NavEnum.CreateNewNoteScreen.name,
                enterTransition = {
                    return@composable fadeIn(tween(700))
                }, popExitTransition = {
                    return@composable fadeOut(tween(700))
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
                    return@composable fadeIn(tween(700))
                }, popExitTransition = {
                    return@composable fadeOut(tween(700))
                }
            ){
                ProfileScreen(
                    navController = navController,
                    hoursSinceLastLogin = hoursSinceLastLogin,
                    putUserDetail = { RaionAPIViewModel.putUserDetail(it[0], it[1], it[2]) },
                    noteList = noteList,
                    userDetailList = listOfUserDetail,
                    userDetailLoadingValue = userDetailLoadingValue,
                    getAPIData = { getAPIData.value = it},
                    removeToken = { RaionAPIViewModel.addToken(tokenId = it, timestamp = "2006-04-22T12:00:00.000000")}
                )
            }

            composable(
                NavEnum.DownloadScreen.name,
                enterTransition = {
                    return@composable fadeIn(tween(700))
                }, popExitTransition = {
                    return@composable fadeOut(tween(700))
                }
            ){
                DownloadScreen(
                    noteDAOViewModel = NoteDAOViewModel,
                    navController = navController,
                    noteList = downloadedNoteList,
                    userDetailList = listOfUserDetail,
                    userDetailLoadingValue = userDetailLoadingValue,
                    getAPIData = { getAPIData.value = it }
                )
            }
        }
    }
}