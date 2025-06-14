package com.backcube.economyapp.features.common.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.backcube.economyapp.R
import com.backcube.economyapp.ui.theme.LightGreen

private val BASE_HEIGHT = 70.dp
private val SMALL_HEIGHT = 56.dp

@Composable
fun CustomListItem(
    modifier: Modifier = Modifier,
    height: Dp? = null,
    isSmallItem: Boolean = false,
    showSeparator: Boolean = true,
    title: String,
    subtitle: String? = null,
    leadingEmoji: String? = null,
    showLeading: Boolean = true,
    leadingBackground: Color = LightGreen,
    trailingText: String? = null,
    trailingSubText: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    showTrailingIcon: Boolean = true,
    showCurrency: Boolean = true,
    onItemClick: (() -> Unit)? = null
) {
    val colors = MaterialTheme.colorScheme

    val finalHeight = when {
        height != null -> height
        isSmallItem -> SMALL_HEIGHT
        else -> BASE_HEIGHT
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(finalHeight - 1.dp)
            .padding(bottom = 1.dp)
            .then(
                if (onItemClick != null) Modifier.clickable { onItemClick() } else Modifier
            )
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (showLeading && leadingEmoji?.isBlank() != true) {
                LeadingContent(
                    emoji = leadingEmoji,
                    title = title,
                    background = leadingBackground
                )
                Spacer(modifier = Modifier.width(16.dp))
            }

            Column(
                Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                subtitle?.let {
                    Text(
                        text = it,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.W400,
                        color = colors.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center
            ) {
                if (!trailingText.isNullOrBlank()) {
                    val currency = if (showCurrency) " ₽" else ""
                    Text(
                        text = "$trailingText$currency",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = colors.onSurface
                    )
                }
                if (!trailingSubText.isNullOrBlank()) {
                    Text(
                        text = trailingSubText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W400,
                        color = colors.onSurface
                    )
                }
            }

            if (showTrailingIcon) {
                Spacer(modifier = Modifier.width(16.dp))
                if (trailingContent != null) {
                    trailingContent.invoke()
                } else {
                    Image(
                        painter = painterResource(R.drawable.ic_more),
                        contentDescription = null
                    )
                }
            }
        }

        if (showSeparator) {
            HorizontalDivider(
                thickness = 1.dp,
                color = colors.outlineVariant
            )
        }
    }
}

@Composable
private fun LeadingContent(
    emoji: String?,
    title: String,
    background: Color
) {
    val text = emoji ?: run {
        val words = title
            .trim()
            .split("\\s+".toRegex())
            .filter { it.isNotEmpty() }
        when {
            words.size >= 2 -> {
                val first = words[0].firstOrNull()?.toString().orEmpty()
                val second = words[1].firstOrNull()?.toString().orEmpty()
                (first + second).uppercase()
            }
            words.size == 1 -> {
                val w = words[0]
                if (w.length >= 2) {
                    w.substring(0, 2).uppercase()
                } else {
                    w.firstOrNull()?.toString()?.uppercase().orEmpty()
                }
            }
            else -> {
                ""
            }
        }
    }

    Box(
        modifier = Modifier
            .size(24.dp)
            .background(color = background, shape = CircleShape),
        contentAlignment = Alignment.Center
    ) {
        val colors = MaterialTheme.colorScheme
        val textSize = emoji?.let { 16.sp } ?: 10.sp
        Text(
            text = text,
            fontSize = textSize,
            fontWeight = FontWeight.Bold,
            color = colors.onSurface
        )
    }
}

// ===== Previews =====

@Preview(showBackground = true)
@Composable
fun Preview_Default() {
    CustomListItem(
        title = "На собачку",
        leadingEmoji = "👤",
        trailingText = "100 000"
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_DefaultWithSubTrailingText() {
    CustomListItem(
        title = "На собачку",
        leadingEmoji = "👤",
        trailingText = "100 000",
        trailingSubText = "22:05"
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_NoSeparator() {
    CustomListItem(
        title = "Настройки",
        leadingEmoji = "\uD83D\uDC57",
        showSeparator = false,
        trailingText = "",
        showTrailingIcon = false
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_NoLeading() {
    CustomListItem(
        title = "На собачку",
        showLeading = false,
        trailingText = "5",
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_WithSubtitleAndIcon() {
    CustomListItem(
        title = "Тутуту",
        subtitle = "Энни",
        leadingEmoji = null,
        trailingText = "5"
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_WithSubtitleAndIconAndEllipsize() {
    CustomListItem(
        title = "На собачку, а может и кошку, а может вообще нет",
        subtitle = "Энни или не Энни? Вот в чем вопрос длиннный предлинный",
        leadingEmoji = null,
        trailingText = "5"
    )
}
