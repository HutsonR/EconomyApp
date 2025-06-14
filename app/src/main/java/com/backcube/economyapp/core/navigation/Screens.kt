package com.backcube.economyapp.core.navigation

sealed class Screens(val route: String) {

    data object SplashScreen : Screens("splash")

    data object AccountScreen : Screens("account")

    data object ArticlesScreen : Screens("article")

    data object ExpensesScreen : Screens("expense")

    data object IncomesScreen : Screens("income")

    data object SettingsScreen : Screens("setting")

}