package com.backcube.economyapp.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.backcube.economyapp.App.Companion.appComponent
import com.backcube.economyapp.main.navigation.AppNavHost
import com.backcube.economyapp.main.navigation.DoubleBackPressToExit
import com.backcube.economyapp.main.navigation.ProtectedNavController
import com.backcube.economyapp.main.viewmodels.NetworkViewModel
import com.backcube.ui.baseComponents.navbar.BottomNavBar
import com.backcube.ui.baseComponents.navbar.NavBarItem
import com.backcube.ui.theme.EconomyAppTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var networkViewModel: NetworkViewModel

    private lateinit var navController: NavHostController

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        applicationContext.appComponent.inject(this)

        setupWindow()

        setContent {
            navController = rememberNavController()

            EconomyAppTheme {
                AppScreen()
            }
        }
    }

    @Composable
    fun AppScreen() {
        val context = LocalContext.current
        val isConnected by networkViewModel.isConnected.collectAsStateWithLifecycle()
        val navController = rememberNavController()
        val navigator = remember {
            ProtectedNavController(navController)
        }

        DoubleBackPressToExit(
            navController,
            NavBarItem.all.map { it.route }
        ) {
            (context as? Activity)?.finish()
        }

        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavBar(
                    isInternetConnected = isConnected,
                    currentRoute = currentRoute,
                    onItemClick = {
                        navController.navigate(it) {
                            popUpTo(0)
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                protectedNavController = navigator,
                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
            )
        }
    }

    private fun setupWindow() {
        enableEdgeToEdge()
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        WindowCompat.setDecorFitsSystemWindows(window, false)
        val controller = WindowInsetsControllerCompat(window, window.decorView)
        controller.isAppearanceLightStatusBars = true
        controller.isAppearanceLightNavigationBars = false
    }
}