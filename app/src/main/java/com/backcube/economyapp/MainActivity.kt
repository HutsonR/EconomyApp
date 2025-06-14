package com.backcube.economyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.backcube.economyapp.core.navigation.Screens
import com.backcube.economyapp.features.account.AccountScreenRoot
import com.backcube.economyapp.features.articles.ArticlesScreenRoot
import com.backcube.economyapp.features.common.baseComponents.BottomNavBar
import com.backcube.economyapp.features.common.baseComponents.NavBarItem
import com.backcube.economyapp.features.expenses.ExpensesScreenRoot
import com.backcube.economyapp.features.income.IncomesScreenRoot
import com.backcube.economyapp.features.settings.SettingsScreenRoot
import com.backcube.economyapp.features.splash.SplashScreenRoot
import com.backcube.economyapp.ui.theme.EconomyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setupWindowDecor()

        setContent {
            navController = rememberNavController()

            EconomyAppTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        BottomNavBar(
                            items = createNavBarItems(),
                            navController = navController
                        )
                    }
                ) { innerPadding ->
                    NavigationHost(
                        modifier = Modifier
                            .padding(bottom = innerPadding.calculateBottomPadding())
                    )
                }
            }
        }
    }

    @Composable
    private fun NavigationHost(modifier: Modifier) {
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = Screens.SplashScreen.route
        ) {
            composable(Screens.SplashScreen.route) {
                SplashScreenRoot(
                    navController = navController
                )
            }
            composable(Screens.AccountScreen.route) {
                AccountScreenRoot(
                    navController = navController
                )
            }
            composable(Screens.ArticlesScreen.route) {
                ArticlesScreenRoot(
                    navController = navController
                )
            }
            composable(Screens.ExpensesScreen.route) {
                ExpensesScreenRoot(
                    navController = navController
                )
            }
            composable(Screens.IncomesScreen.route) {
                IncomesScreenRoot(
                    navController = navController
                )
            }
            composable(Screens.SettingsScreen.route) {
                SettingsScreenRoot(
                    navController = navController
                )
            }
        }
    }

    private fun setupWindowDecor() {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = false
    }

    private fun createNavBarItems() = listOf(
        NavBarItem(
            label = R.string.navbar_expenses,
            icon = R.drawable.ic_expense,
            route = Screens.ExpensesScreen.route
        ),
        NavBarItem(
            label = R.string.navbar_incomes,
            icon = R.drawable.ic_income,
            route = Screens.IncomesScreen.route
        ),
        NavBarItem(
            label = R.string.navbar_account,
            icon = R.drawable.ic_account,
            route = Screens.AccountScreen.route
        ),
        NavBarItem(
            label = R.string.navbar_articles,
            icon = R.drawable.ic_article,
            route = Screens.ArticlesScreen.route
        ),
        NavBarItem(
            label = R.string.navbar_settings,
            icon = R.drawable.ic_setting,
            route = Screens.SettingsScreen.route
        )
    )
}