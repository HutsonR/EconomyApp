package com.backcube.ui.components.graphics

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.backcube.ui.R
import java.util.Locale

@Composable
fun DonutChart(
    modifier: Modifier = Modifier,
    items: List<CategorySpendingDonutUiModel>,
    animationDuration: Int = 1000
) {
    val context = LocalContext.current
    val colors = MaterialTheme.colorScheme
    val resultItems = createListForStats(
        items,
        context.getString(R.string.other)
    )
    val totalSum = resultItems.sumOf { it.percentage.toDouble() }.toFloat()
    if (totalSum == 0f) return

    val animatedProgress = remember { Animatable(0f) }

    LaunchedEffect(resultItems) {
        animatedProgress.snapTo(0f)
        animatedProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = animationDuration)
        )
    }

    val sweepAngles = resultItems.map {
        360 * (it.percentage / totalSum)
    }

    BoxWithConstraints(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        val strokeWidth = 8.dp
        val strokeWidthPx = with(LocalDensity.current) { strokeWidth.toPx() }
        val chartSize = if (this.maxWidth < this.maxHeight) this.maxWidth else this.maxHeight

        Canvas(
            modifier = Modifier.size(chartSize)
        ) {
            var startAngle = -90f
            for (i in resultItems.indices) {
                drawArc(
                    color = resultItems[i].color,
                    startAngle = startAngle,
                    sweepAngle = sweepAngles[i] * animatedProgress.value,
                    useCenter = false,
                    style = Stroke(width = strokeWidthPx, cap = StrokeCap.Butt)
                )
                startAngle += sweepAngles[i] * animatedProgress.value
            }
        }

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {
            resultItems
                .sortedByDescending { it.percentage }
                .forEach { item ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(
                        modifier = Modifier.size(5.dp),
                        shape = CircleShape,
                        color = item.color
                    ) {}
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = "${String.format(Locale.getDefault(), "%.1f", item.percentage)}% ${item.title}",
                        color = colors.onSurface,
                        fontSize = 9.sp
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDonut() {
    val mockCategorySpendingList = listOf(
        CategorySpendingDonutUiModel(
            title = "Продукты",
            percentage = 20f,
            color = Color(0xFF4CAF50)
        ),
        CategorySpendingDonutUiModel(
            title = "Транспорт",
            percentage = 25f,
            color = Color(0xFF2196F3)
        ),
        CategorySpendingDonutUiModel(
            title = "Развлечения",
            percentage = 15f,
            color = Color(0xFF9C27B0)
        ),
        CategorySpendingDonutUiModel(
            title = "Жилье",
            percentage = 20f,
            color = Color(0xFFFF9800)
        ),
        CategorySpendingDonutUiModel(
            title = "Здоровье",
            percentage = 5f,
            color = Color(0xFFF44336)
        ),
        CategorySpendingDonutUiModel(
            title = "Путешествия",
            percentage = 15f,
            color = Color(0xFFCDF321)
        )
    )
    DonutChart(
        items = mockCategorySpendingList
    )
}

private fun createListForStats(
    items: List<CategorySpendingDonutUiModel>,
    lastItemText: String
): List<CategorySpendingDonutUiModel> {
    if (items.size <= 5) return items

    val sortedItems = items.sortedByDescending { it.percentage }
    val biggestItems = sortedItems.take(4)
    val fifthItem = sortedItems[4]
    val otherItemsPercentage = sortedItems.drop(4).sumOf { it.percentage.toDouble() }.toFloat()

    val otherItem = fifthItem.copy(
        title = lastItemText,
        percentage = otherItemsPercentage,
        color = Color.Gray
    )

    return biggestItems + otherItem
}

/**
 * @param title Название категории
 * @param percentage Процент от общей суммы
 * @param color Цвет в графике
 */
data class CategorySpendingDonutUiModel(
    val title: String,
    val percentage: Float,
    val color: Color
)