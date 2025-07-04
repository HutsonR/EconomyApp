package com.backcube.economyapp.core.ui.baseComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.backcube.economyapp.core.navigation.NavBarItem
import com.backcube.economyapp.core.ui.theme.LightGreen
import com.backcube.economyapp.core.ui.theme.UltraGreen

/**
 * Кастомная нижняя навигационная панель (Bottom Navigation Bar).
 *
 * Отображает список навигационных элементов с иконками и подписями. Поддерживает подсветку активного пункта
 * и автоматическую навигацию через [NavHostController]. Использует цвета темы Material и собственные цвета выделения.
 *
 * @param items Список элементов навигации, каждый из которых содержит route, label и иконку ([NavBarItem]).
 * @param navController Контроллер навигации, с которым связан NavHost.
 * @param modifier [Modifier] для кастомизации внешнего вида панели.
 */
@Composable
fun BottomNavBar(
    items: List<NavBarItem>,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    NavigationBar(
        modifier = modifier,
        containerColor = colors.surfaceContainer
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            items.forEach {
                val selected = currentRoute == it.route
                val activeBackground = LightGreen
                val activeIconColor = UltraGreen
                val inactiveIconColor = colors.onSurfaceVariant

                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(it.icon),
                            contentDescription = stringResource(id = it.label),
                            tint = if (selected) activeIconColor else inactiveIconColor
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(id = it.label),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelMedium,
                            color = colors.onSurfaceVariant,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.widthIn(max = 80.dp)
                        )
                    },
                    selected = selected,
                    onClick = {
                        if (currentRoute != it.route) {
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = activeIconColor,
                        unselectedIconColor = inactiveIconColor,
                        indicatorColor = if (selected) activeBackground else Color.Transparent
                    )
                )
            }
        }
    }
}


