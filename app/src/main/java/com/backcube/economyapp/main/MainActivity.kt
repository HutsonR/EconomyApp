package com.backcube.economyapp.main

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.backcube.economyapp.R
import com.backcube.economyapp.core.navigation.NavBarItem
import com.backcube.economyapp.features.common.baseComponents.BottomNavBar
import com.backcube.economyapp.main.viewmodels.NetworkViewModel
import com.backcube.economyapp.navigation.AppNavHost
import com.backcube.economyapp.ui.theme.EconomyAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var navController: NavHostController

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            bottomBar = {
                BottomNavBar(
                    items = NavBarItem.all,
                    navController = navController
                )
            }
        ) { innerPadding ->
            AppNavHost(
                navController = navController,
                modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
            )
        }

        NetworkSnackbar()
    }

    @Composable
    fun NetworkSnackbar(viewModel: NetworkViewModel = hiltViewModel()) {
        val isConnected by viewModel.isConnected.collectAsStateWithLifecycle()

        if (!isConnected) {
            Snackbar(
                modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp, horizontal = 16.dp)
            ) {
                Text(stringResource(R.string.no_internet_connection))
            }
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