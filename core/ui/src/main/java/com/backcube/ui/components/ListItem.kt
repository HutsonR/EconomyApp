package com.backcube.ui.components

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
import com.backcube.domain.utils.CurrencyIsoCode
import com.backcube.ui.R
import com.backcube.ui.utils.toCurrency

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
    leadingEmojiOrText: String? = null,
    leadingContent: @Composable (() -> Unit)? = null,
    showLeading: Boolean = true,
    leadingBackground: Color = MaterialTheme.colorScheme.surfaceVariant,
    trailingText: String? = null,
    trailingSubText: String? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    showTrailingIcon: Boolean = true,
    currencyIsoCode: CurrencyIsoCode? = null,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    trailingTextColor: Color = MaterialTheme.colorScheme.onSurface,
    trailingSubTextColor: Color = MaterialTheme.colorScheme.onSurface,
    onItemClick: (() -> Unit)? = null
) {
    val colors = MaterialTheme.colorScheme
    val finalHeight = resolveHeight(height, isSmallItem)

    val clickableModifier = if (onItemClick != null) Modifier.clickable { onItemClick() } else Modifier

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(finalHeight - 1.dp)
            .padding(bottom = 1.dp)
            .then(clickableModifier)
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            if (showLeading) {
                when {
                    leadingContent != null -> {
                        leadingContent()
                    }
                    leadingEmojiOrText?.isBlank() != true ->  {
                        LeadingContent(
                            emoji = leadingEmojiOrText,
                            title = title,
                            background = leadingBackground
                        )
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
            }

            MainContentColumn(
                title = title,
                subtitle = subtitle,
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                textColor = titleColor,
                subtitleColor = subtitleColor
            )

            TrailingContentColumn(
                trailingText = trailingText,
                trailingSubText = trailingSubText,
                trailingContent = trailingContent,
                currency = currencyIsoCode?.toCurrency(),
                showTrailingIcon = showTrailingIcon,
                trailingTextColor = trailingTextColor,
                trailingSubTextColor = trailingSubTextColor
            )
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
    val text = emoji ?: getInitialsFromTitle(title)

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

@Composable
private fun MainContentColumn(
    title: String,
    subtitle: String?,
    modifier: Modifier,
    textColor: Color,
    subtitleColor: Color
) {
    Column(modifier = modifier) {
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            color = textColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        if (!subtitle.isNullOrBlank()) {
            Text(
                text = subtitle,
                fontSize = 14.sp,
                fontWeight = FontWeight.W400,
                color = subtitleColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun TrailingContentColumn(
    trailingText: String?,
    trailingSubText: String?,
    trailingContent: @Composable (() -> Unit)?,
    currency: String?,
    showTrailingIcon: Boolean,
    trailingTextColor: Color,
    trailingSubTextColor: Color
) {
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        if (!trailingText.isNullOrBlank() || currency != null) {
            val displayText = when {
                trailingText.isNullOrBlank() -> currency ?: ""
                currency.isNullOrBlank() -> trailingText
                else -> "$trailingText $currency"
            }

            Text(
                text = displayText,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = trailingTextColor
            )
        }
        if (!trailingSubText.isNullOrBlank()) {
            Text(
                text = trailingSubText,
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = trailingSubTextColor
            )
        }
    }

    if (showTrailingIcon) {
        Spacer(modifier = Modifier.width(16.dp))
        trailingContent?.invoke() ?: Image(
            painter = painterResource(R.drawable.ic_more),
            contentDescription = null
        )
    }
}

private fun getInitialsFromTitle(title: String): String {
    val words = title
        .trim()
        .split("\\s+".toRegex())
        .filter { it.isNotEmpty() }

    return when {
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

        else -> ""
    }
}

private fun resolveHeight(height: Dp?, isSmallItem: Boolean): Dp {
    return height ?: if (isSmallItem) SMALL_HEIGHT else BASE_HEIGHT
}

// ===== Previews =====

@Preview(showBackground = true)
@Composable
fun Preview_Default() {
    CustomListItem(
        title = "На собачку",
        leadingEmojiOrText = "👤",
        trailingText = "100 000",
        currencyIsoCode = CurrencyIsoCode.RUB
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_DefaultWithSubTrailingText() {
    CustomListItem(
        title = "На собачку",
        leadingEmojiOrText = "👤",
        trailingText = "100 000",
        trailingSubText = "22:05",
        currencyIsoCode = CurrencyIsoCode.RUB
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_NoSeparator() {
    CustomListItem(
        title = "Настройки",
        leadingEmojiOrText = "\uD83D\uDC57",
        showSeparator = false,
        trailingText = "",
        showTrailingIcon = false
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_NoLeading_WithUSD() {
    CustomListItem(
        title = "На собачку",
        showLeading = false,
        trailingText = "5",
        currencyIsoCode = CurrencyIsoCode.USD
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_WithSubtitleAndIcon_WithCurrencyOnly() {
    CustomListItem(
        title = "Тутуту",
        subtitle = "Энни",
        leadingEmojiOrText = null,
        currencyIsoCode = CurrencyIsoCode.RUB
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_WithSubtitleAndIconAndEllipsize() {
    CustomListItem(
        title = "На собачку, а может и кошку, а может вообще нет",
        subtitle = "Энни или не Энни? Вот в чем вопрос длиннный предлинный",
        leadingEmojiOrText = null,
        trailingText = "5",
        currencyIsoCode = CurrencyIsoCode.RUB
    )
}

@Preview(showBackground = true)
@Composable
fun Preview_WithSubtitleAndIconAndLeading() {
    CustomListItem(
        title = "На собачку, а может и кошку, а может вообще нет",
        subtitle = "Энни или не Энни? Вот в чем вопрос длиннный предлинный",
        leadingContent = {
            Image(
                painter = painterResource(R.drawable.ic_cancel),
                contentDescription = null
            )
        },
        showTrailingIcon = false
    )
}
