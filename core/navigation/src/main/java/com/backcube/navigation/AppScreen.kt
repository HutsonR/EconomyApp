package com.backcube.navigation

import android.net.Uri

sealed class AppScreen(val route: String) {

    object Splash : AppScreen("splash")
    object Account : AppScreen("account")
    object Articles : AppScreen("articles")
    object Expenses : AppScreen("expenses")
    object Incomes : AppScreen("incomes")
    object Settings : AppScreen("settings")

    data class History(val isIncome: Boolean) : AppScreen("history") {
        fun createRoute() = "$route/${Uri.encode(isIncome.toString())}"
        companion object {
            const val ARG_KEY = "isIncome"
        }
    }

    data class AccountEdit(val accountId: Int) : AppScreen("account_edit") {
        fun createRoute() = "$route/${Uri.encode(accountId.toString())}"
        companion object {
            const val ARG_KEY = "accountId"
        }
    }
}
