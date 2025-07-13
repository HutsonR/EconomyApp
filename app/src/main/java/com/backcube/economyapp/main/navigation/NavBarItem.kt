package com.backcube.economyapp.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.backcube.economyapp.R
import com.backcube.economyapp.core.navigation.Screens

sealed class NavBarItem(
    @StringRes val label: Int,
    @DrawableRes val icon: Int,
    val route: String
) {
    data object Expenses : NavBarItem(R.string.navbar_expenses, R.drawable.ic_expense, Screens.ExpensesScreen.route)
    data object Incomes : NavBarItem(R.string.navbar_incomes, R.drawable.ic_income, Screens.IncomesScreen.route)
    data object Account : NavBarItem(R.string.navbar_account, R.drawable.ic_account, Screens.AccountScreen.route)
    data object Articles : NavBarItem(R.string.navbar_articles, R.drawable.ic_article, Screens.ArticlesScreen.route)
    data object Settings : NavBarItem(R.string.navbar_settings, R.drawable.ic_setting, Screens.SettingsScreen.route)

    companion object {
        val all = listOf(Expenses, Incomes, Account, Articles, Settings)
    }
}
