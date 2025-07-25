package com.backcube.ui.baseComponents.navbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.backcube.ui.baseComponents.NetworkStatusBanner
import com.backcube.ui.utils.LocalAppContext

/**
 * Кастомная нижняя навигационная панель (Bottom Navigation Bar).
 *
 * Отображает список навигационных элементов с иконками и подписями. Использует цвета темы Material и собственные цвета выделения.
 *
 * @param modifier [Modifier] для кастомизации внешнего вида панели.
 * @param isInternetConnected Флаг, указывающий на наличие интернета.
 * @param currentRoute Текущая активная route навигации.
 * @param onItemClick Callback для обработки нажатия на элемент навигации.
 */
@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    isInternetConnected: Boolean = true,
    currentRoute: String?,
    onItemClick: (String) -> Unit
) {
    val context = LocalAppContext.current
    val colors = MaterialTheme.colorScheme
    val items = NavBarItem.all

    Column {
        NetworkStatusBanner(isInternetConnected)
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
                    val activeBackground = colors.surfaceVariant
                    val activeIconColor = colors.primary
                    val inactiveIconColor = colors.onSurfaceVariant

                    NavigationBarItem(
                        icon = {
                            Icon(
                                painter = painterResource(it.icon),
                                contentDescription = context.getString(it.label),
                                tint = if (selected) activeIconColor else inactiveIconColor
                            )
                        },
                        label = {
                            Text(
                                text = context.getString(it.label),
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
                                onItemClick(it.route)
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
}


