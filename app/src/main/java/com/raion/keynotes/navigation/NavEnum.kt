package com.raion.keynotes.navigation

enum class NavEnum {
    HomeScreen,
    NoteScreen
    ;

    companion object{
        fun fromRoute(route: String?): NavEnum
                = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            NoteScreen.name -> NoteScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}