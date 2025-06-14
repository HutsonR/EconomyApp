package com.backcube.economyapp.features.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.backcube.economyapp.core.navigation.Screens

@Composable
fun SplashScreenRoot(
    navController: NavController
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        navController.navigate(Screens.ExpensesScreen.route) {
            popUpTo(navController.graph.startDestinationId) { inclusive = true }
        }
    }
}