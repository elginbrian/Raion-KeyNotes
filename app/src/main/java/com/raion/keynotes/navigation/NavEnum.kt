package com.raion.keynotes.navigation

enum class NavEnum {
    HomeScreen,
    NoteScreen,
    ProfileScreen,
    DownloadScreen
    ;

    companion object{
        fun fromRoute(route: String?): NavEnum
                = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            NoteScreen.name -> NoteScreen
            ProfileScreen.name -> ProfileScreen
            DownloadScreen.name -> DownloadScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}