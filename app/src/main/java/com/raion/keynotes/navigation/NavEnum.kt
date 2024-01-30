package com.raion.keynotes.navigation

enum class NavEnum {
    HomeScreen,
    NoteScreen,
    ProfileScreen,
    DownloadScreen,
    LoginScreen,
    CreateNewNoteScreen,
    DownloadedNoteScreen
    ;

    companion object{
        fun fromRoute(route: String?): NavEnum
                = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            NoteScreen.name -> NoteScreen
            ProfileScreen.name -> ProfileScreen
            DownloadScreen.name -> DownloadScreen
            LoginScreen.name -> LoginScreen
            CreateNewNoteScreen.name -> CreateNewNoteScreen
            DownloadedNoteScreen.name -> DownloadedNoteScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}