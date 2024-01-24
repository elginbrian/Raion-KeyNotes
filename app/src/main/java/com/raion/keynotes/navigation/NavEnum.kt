package com.raion.keynotes.navigation

enum class NavEnum {
    HomeScreen;

    companion object{
        fun fromRoute(route: String?): NavEnum
                = when(route?.substringBefore("/")){
            HomeScreen.name -> HomeScreen
            null -> HomeScreen
            else -> throw IllegalArgumentException("Route $route is not recognized")
        }
    }
}