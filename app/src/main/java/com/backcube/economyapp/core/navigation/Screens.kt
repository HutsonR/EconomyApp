package com.backcube.economyapp.core.navigation

import android.net.Uri

sealed class Screens(val route: String) {

    data object SplashScreen : Screens("splash")

    data object AccountScreen : Screens("account")

    data object ArticlesScreen : Screens("article")

    data object ExpensesScreen : Screens("expense")

    data object IncomesScreen : Screens("income")

    data object SettingsScreen : Screens("setting")

    data object HistoryScreen : Screens("history") {
        fun createRoute(isIncome: Boolean) = "history/${Uri.encode(isIncome.toString())}"
    }

    data object AccountEditScreen : Screens("account_edit") {
        fun createRoute(accountId: String) = "account_edit/${Uri.encode(accountId)}"
    }

    data object TransactionEditScreen : Screens("transaction_edit") {
        fun createRoute(
            transactionId: String,
            isIncome: Boolean
        ) = "transaction_edit/${Uri.encode(transactionId)}/${Uri.encode(isIncome.toString())}"
    }

}