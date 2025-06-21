package com.backcube.economyapp.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.backcube.economyapp.core.navigation.Screens
import com.backcube.economyapp.features.account.AccountScreenRoot
import com.backcube.economyapp.features.articles.ArticlesScreenRoot
import com.backcube.economyapp.features.expenses.ExpensesScreenRoot
import com.backcube.economyapp.features.histories.HistoryScreenRoot
import com.backcube.economyapp.features.income.IncomesScreenRoot
import com.backcube.economyapp.features.settings.SettingsScreenRoot
import com.backcube.economyapp.features.splash.SplashScreenRoot

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.SplashScreen.route
    ) {
        // Main screens
        composable(Screens.SplashScreen.route) {
            SplashScreenRoot(navController)
        }
        composable(Screens.AccountScreen.route) {
            AccountScreenRoot(navController)
        }
        composable(Screens.ArticlesScreen.route) {
            ArticlesScreenRoot(navController)
        }
        composable(Screens.ExpensesScreen.route) {
            ExpensesScreenRoot(navController)
        }
        composable(Screens.IncomesScreen.route) {
            IncomesScreenRoot(navController)
        }
        composable(Screens.SettingsScreen.route) {
            SettingsScreenRoot(navController)
        }

        // Secondary screens
        composable(
            route = Screens.HistoryScreen.route + "/{isIncome}",
            arguments = listOf(navArgument("isIncome") { type = NavType.StringType })
        ) { stackEntry ->
            val isIncome = Uri.decode(stackEntry.arguments?.getString("isIncome"))
            HistoryScreenRoot(
                isIncome.toBoolean(),
                navController = navController
            )
        }
    }
}
