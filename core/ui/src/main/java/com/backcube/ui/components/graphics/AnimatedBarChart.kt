package com.backcube.ui.components.graphics

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.util.Locale

/**
 * UI модель точки на графике
 *
 * @param xLabel подпись по оси X
 * @param value значение по Y (баланс)
 * @param color Цвет точки/линии
 */
data class ChartPoint(
    val xLabel: String,
    val value: Float,
    val color: Color
)

enum class ChartType { Bar, Line }

@Composable
fun AnimatedChartView(
    points: List<ChartPoint>,
    chartType: ChartType,
    modifier: Modifier = Modifier
) {
    if (points.isEmpty()) return

    val animProgress = remember { Animatable(0f) }

    LaunchedEffect(points) {
        animProgress.snapTo(0f)
        animProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
        )
    }

    val labelIndices = remember(points.size) {
        when (points.size) {
            1 -> listOf(0)
            2 -> listOf(0, 1)
            else -> listOf(0, points.size / 2, points.lastIndex)
        }
    }

    // Максимальное значение для нормализации
    val maxVal = points.maxOf { it.value }.coerceAtLeast(1f)

    Box(
        modifier = modifier
            .fillMaxSize()
            .clipToBounds()
    ) {
        Canvas(modifier = modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 24.dp)) {
            val w = size.width
            val h = size.height
            val count = points.size
            val spacing = w / (count * 1f)
            val barWidth = spacing * 0.6f

            // Горизонтальные сеточные линии
            val gridLines = 4
            for (i in 0..gridLines) {
                val y = h - (h / gridLines) * i
                drawLine(
                    color = Color.LightGray.copy(alpha = 0.5f),
                    start = Offset(0f, y),
                    end = Offset(w, y),
                    strokeWidth = 1.dp.toPx(),
                    pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                )
                // Y-метки
                val value = (maxVal / gridLines) * i
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        String.format(Locale.getDefault(), "%.0f", value),
                        0f,
                        y - 4.dp.toPx(),
                        android.graphics.Paint().apply {
                            textSize = 9.sp.toPx()
                            color = android.graphics.Color.DKGRAY
                            textAlign = android.graphics.Paint.Align.LEFT
                        }
                    )
                }
            }

            when (chartType) {
                ChartType.Bar -> {
                    points.forEachIndexed { idx, pt ->
                        val x = spacing * idx + spacing / 2
                        val targetBarHeight = (pt.value / maxVal) * h
                        val animatedHeight = targetBarHeight * animProgress.value

                        drawRoundRect(
                            color = pt.color,
                            topLeft = Offset(x, h - animatedHeight),
                            size = Size(barWidth, animatedHeight),
                            cornerRadius = CornerRadius(6.dp.toPx())
                        )
                    }
                }
                ChartType.Line -> {
                    val path = androidx.compose.ui.graphics.Path()
                    points.forEachIndexed { idx, pt ->
                        val x = spacing * idx + spacing / 2 + barWidth / 2
                        val y = h - (pt.value / maxVal) * h
                        if (idx == 0) path.moveTo(x, y) else path.lineTo(x, y)
                    }

                    val pathMeasure = android.graphics.PathMeasure(path.asAndroidPath(), false)
                    val segmentPath = androidx.compose.ui.graphics.Path()
                    val length = pathMeasure.length
                    pathMeasure.getSegment(0f, length * animProgress.value, segmentPath.asAndroidPath(), true)
                    drawPath(
                        path = segmentPath,
                        color = Color(0xFF3F51B5),
                        style = Stroke(width = 3.dp.toPx(), cap = Stroke.DefaultCap)
                    )
                    // Точки
                    points.forEachIndexed { idx, pt ->
                        val x = spacing * idx + spacing / 2 + barWidth / 2
                        val y = h - (pt.value / maxVal) * h
                        drawCircle(
                            color = pt.color,
                            center = Offset(x, y),
                            radius = 5.dp.toPx()
                        )
                    }
                }
            }

            labelIndices.forEach { idx ->
                val pt = points[idx]
                val x = spacing * idx + spacing / 2 + barWidth / 2
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        pt.xLabel,
                        x,
                        h + 20.dp.toPx(),
                        android.graphics.Paint().apply {
                            textSize = 9.sp.toPx()
                            color = android.graphics.Color.DKGRAY
                            textAlign = android.graphics.Paint.Align.CENTER
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimatedChartView_Bar() {
    val sample = listOf(
        ChartPoint("01.02", 200f, Color(0xFFFF5C00)),
        ChartPoint("02.02", 300f, Color(0xFFFF5C00)),
        ChartPoint("03.02", 100f, Color(0xFF2EE96B)),
        ChartPoint("04.02", 400f, Color(0xFFFF5C00)),
        ChartPoint("05.02", 250f, Color(0xFF2EE96B))
    )
    AnimatedChartView(
        points = sample,
        chartType = ChartType.Bar,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(Color(0xFFF0F0F0))
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimatedChartView_Line() {
    val sample = listOf(
        ChartPoint("01.02", 200f, Color(0xFFFF5C00)),
        ChartPoint("02.02", 300f, Color(0xFFFF5C00)),
        ChartPoint("03.02", 100f, Color(0xFF2EE96B)),
        ChartPoint("04.02", 400f, Color(0xFFFF5C00)),
        ChartPoint("05.02", 250f, Color(0xFF2EE96B))
    )
    AnimatedChartView(
        points = sample,
        chartType = ChartType.Line,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(Color(0xFFF0F0F0))
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewAnimatedChartView_TwoPoints() {
    val sample = listOf(
        ChartPoint("01.02", 200f, Color(0xFFFF5C00)),
        ChartPoint("02.02", 300f, Color(0xFFFF5C00)),
    )
    AnimatedChartView(
        points = sample,
        chartType = ChartType.Bar,
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .background(Color(0xFFF0F0F0))
    )
}