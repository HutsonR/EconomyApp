package com.backcube.economyapp.main.navigation

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.backcube.economyapp.R
import kotlinx.coroutines.delay

/**
 * Обрабатывает двойное нажатие кнопки "Назад" для выхода из приложения.
 *
 * Показывает предупреждающий Toast при первом нажатии на корневом экране и выходит из приложения при повторном нажатии.
 *
 * @param navController Контроллер навигации для отслеживания текущего экрана.
 * @param rootRoutes Список маршрутов, считающихся корневыми (вкладки BottomNav).
 * @param onExit Действие, которое будет выполнено при подтверждённом выходе (например, `activity.finish()`).
 */
@Composable
fun DoubleBackPressToExit(
    navController: NavHostController,
    rootRoutes: List<String>,
    onExit: () -> Unit
) {
    val context = LocalContext.current
    var backPressedOnce by remember { mutableStateOf(false) }

    val currentRoute = navController
        .currentBackStackEntryAsState()
        .value
        ?.destination
        ?.route

    val isRootRoute = currentRoute in rootRoutes

    if (isRootRoute) {
        BackHandler {
            if (backPressedOnce) {
                onExit()
            } else {
                backPressedOnce = true
                Toast.makeText(context, context.getText(R.string.exit_double_tap), Toast.LENGTH_SHORT).show()
            }
        }

        LaunchedEffect(backPressedOnce) {
            if (backPressedOnce) {
                delay(2000)
                backPressedOnce = false
            }
        }
    }
}

