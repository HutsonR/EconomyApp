package com.backcube.ui.baseComponents

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Кастомный TopBar с центрированным заголовком и поддержкой edge-to-edge-дизайна.
 *
 * Использует [CenterAlignedTopAppBar] из Material3 и автоматически управляет фоном, цветом контента,
 * и отображением иконок (если заданы).
 *
 * @param title Текст заголовка, выравненный по центру. Отображается в одну строку, обрезается по ellipsis при переполнении.
 * @param modifier [Modifier] для настройки внешнего вида. Например, для установки отступов, высоты и т.д.
 * @param backgroundColor Цвет фона top bar. По умолчанию — [MaterialTheme.colorScheme.primary].
 *                        Можно использовать [Color.Transparent] для прозрачности.
 * @param contentColor Цвет контента: заголовка, иконок. По умолчанию — [MaterialTheme.colorScheme.onSurface].
 * @param leadingIconPainter Иконка слева (например, кнопка "назад"). Если `null`, левая иконка не отображается.
 * @param leadingIconContentDescription Описание левой иконки для accessibility. Рекомендуется передавать осмысленное описание или `null`.
 * @param onLeadingClick Обработчик клика по левой иконке. Срабатывает только если `leadingIconPainter` и `onLeadingClick` заданы.
 * @param trailingIconPainter Иконка справа (например, "настройки" или "поиск"). Если `null`, правая иконка не отображается.
 * @param trailingIconContentDescription Описание правой иконки для accessibility.
 * @param onTrailingClick Обработчик клика по правой иконке. Срабатывает только если `trailingIconPainter` и `onTrailingClick` заданы.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    leadingIconPainter: Painter? = null,
    leadingIconContentDescription: String? = null,
    onLeadingClick: (() -> Unit)? = null,
    trailingIconPainter: Painter? = null,
    trailingIconContentDescription: String? = null,
    onTrailingClick: (() -> Unit)? = null
) {
    CenterAlignedTopAppBar(
        modifier = modifier
            .fillMaxWidth(),
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontSize = 22.sp,
                color = contentColor,
                fontWeight = FontWeight.W400
            )
        },
        navigationIcon = if (leadingIconPainter != null && onLeadingClick != null) {
            {
                IconButton(
                    onClick = onLeadingClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = leadingIconPainter,
                        contentDescription = leadingIconContentDescription,
                        tint = contentColor
                    )
                }
            }
        } else {
            {
                Box(Modifier.size(48.dp))
            }
        },
        actions = if (trailingIconPainter != null && onTrailingClick != null) {
            {
                IconButton(
                    onClick = onTrailingClick,
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        painter = trailingIconPainter,
                        contentDescription = trailingIconContentDescription,
                        tint = contentColor
                    )
                }
            }
        } else {
            {
                Box(Modifier.size(48.dp))
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = backgroundColor,
            titleContentColor = contentColor,
            navigationIconContentColor = contentColor,
            actionIconContentColor = contentColor
        )
    )
}

@Preview(
    name = "TopBar: Title only",
    showBackground = true
)
@Composable
fun Preview_TopBar_TitleOnly() {
    MaterialTheme {
        CustomTopBar(
            title = "Главная",
            leadingIconPainter = null,
            trailingIconPainter = null
        )
    }
}

@Preview(
    name = "TopBar: Leading only",
    showBackground = true
)
@Composable
fun Preview_TopBar_LeadingOnly() {
    MaterialTheme {
        CustomTopBar(
            title = "Подробности",
            leadingIconPainter = rememberVectorPainter(Icons.Default.ArrowBack),
            leadingIconContentDescription = "Назад",
            onLeadingClick = { /* TODO */ },
            trailingIconPainter = null
        )
    }
}

@Preview(
    name = "TopBar: Trailing only",
    showBackground = true
)
@Composable
fun Preview_TopBar_TrailingOnly() {
    MaterialTheme {
        CustomTopBar(
            title = "Список",
            leadingIconPainter = null,
            trailingIconPainter = rememberVectorPainter(Icons.Default.Search),
            trailingIconContentDescription = "Поиск",
            onTrailingClick = { /* TODO */ }
        )
    }
}

@Preview(
    name = "TopBar: Both icons",
    showBackground = true
)
@Composable
fun Preview_TopBar_BothIcons() {
    MaterialTheme {
        CustomTopBar(
            title = "Профиль",
            leadingIconPainter = rememberVectorPainter(Icons.Default.Menu),
            leadingIconContentDescription = "Меню",
            onLeadingClick = { /* TODO */ },
            trailingIconPainter = rememberVectorPainter(Icons.Default.Settings),
            trailingIconContentDescription = "Настройки",
            onTrailingClick = { /* TODO */ }
        )
    }
}

@Preview(
    name = "TopBar: Transparent background",
    showBackground = true,
    backgroundColor = 0xFFFFFF,
)
@Composable
fun Preview_TopBar_Transparent() {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 56.dp)
        ) {
        }
        CustomTopBar(
            title = "Экран с картой",
            leadingIconPainter = rememberVectorPainter(Icons.Default.ArrowBack),
            leadingIconContentDescription = "Назад",
            onLeadingClick = { /* TODO */ },
            trailingIconPainter = rememberVectorPainter(Icons.Default.Search),
            trailingIconContentDescription = "Поиск",
            onTrailingClick = { /* TODO */ },
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(
    name = "TopBar: Custom background",
    showBackground = true
)
@Composable
fun Preview_TopBar_CustomBackground() {
    MaterialTheme {
        CustomTopBar(
            title = "Финансы",
            leadingIconPainter = rememberVectorPainter(Icons.Default.ArrowBack),
            leadingIconContentDescription = "Назад",
            onLeadingClick = { /* TODO */ },
            trailingIconPainter = rememberVectorPainter(Icons.Default.Search),
            trailingIconContentDescription = "Поиск",
            onTrailingClick = { /* TODO */ },
            backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }
}

@Preview(
    name = "TopBar: Long Title Ellipsis",
    showBackground = true
)
@Composable
fun Preview_TopBar_LongTitle() {
    MaterialTheme {
        CustomTopBar(
            title = "Очень длинное название страницы, которое не помещается в одну строку",
            leadingIconPainter = rememberVectorPainter(Icons.Default.ArrowBack),
            leadingIconContentDescription = "Назад",
            onLeadingClick = { },
            trailingIconPainter = null
        )
    }
}