package com.backcube.economyapp.main.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.backcube.account.edit.AccountEditScreenRoot
import com.backcube.account.main.AccountScreenRoot
import com.backcube.articles.ArticlesScreenRoot
import com.backcube.navigation.AppNavigationController
import com.backcube.navigation.model.Screens
import com.backcube.settings.SettingsScreenRoot
import com.backcube.transactions.presentation.edit.TransactionEditorScreenRoot
import com.backcube.transactions.presentation.histories.HistoryScreenRoot
import com.backcube.transactions.presentation.list.screens.ExpensesScreenRoot
import com.backcube.transactions.presentation.list.screens.IncomesScreenRoot

@Composable
fun AppNavHost(
    navController: NavHostController,
    protectedNavController: AppNavigationController,
    modifier: Modifier = Modifier
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.ExpensesScreen.route
    ) {
        // Main screens
        composable(Screens.AccountScreen.route) {
            AccountScreenRoot(protectedNavController)
        }
        composable(Screens.ArticlesScreen.route) {
            ArticlesScreenRoot(protectedNavController)
        }
        composable(Screens.ExpensesScreen.route) {
            ExpensesScreenRoot(protectedNavController)
        }
        composable(Screens.IncomesScreen.route) {
            IncomesScreenRoot(protectedNavController)
        }
        composable(Screens.SettingsScreen.route) {
            SettingsScreenRoot(protectedNavController)
        }

        // Secondary screens
        composable(
            route = Screens.HistoryScreen.route + "/{isIncome}",
            arguments = listOf(navArgument("isIncome") { type = NavType.StringType })
        ) { stackEntry ->
            val isIncome = Uri.decode(stackEntry.arguments?.getString("isIncome"))
            HistoryScreenRoot(
                isIncome.toBoolean(),
                navController = protectedNavController
            )
        }

        composable(
            route = Screens.AccountEditScreen.route + "/{accountId}",
            arguments = listOf(navArgument("accountId") { type = NavType.StringType })
        ) { stackEntry ->
            val accountId = Uri.decode(stackEntry.arguments?.getString("accountId"))
            AccountEditScreenRoot(
                accountId = accountId.toInt(),
                navController = protectedNavController
            )
        }

        composable(
            route = Screens.TransactionEditScreen.route + "/{transactionId}/{isIncome}",
            arguments = listOf(
                navArgument("transactionId") { type = NavType.StringType },
                navArgument("isIncome") { type = NavType.StringType }
            )
        ) { stackEntry ->
            val transactionId = Uri.decode(stackEntry.arguments?.getString("transactionId"))
            val isIncome = Uri.decode(stackEntry.arguments?.getString("isIncome"))
            TransactionEditorScreenRoot(
                transactionId = transactionId.toInt(),
                isIncome = isIncome.toBoolean(),
                navController = protectedNavController
            )
        }
    }
}
