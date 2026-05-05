package com.nik.assignment.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nik.assignment.model.CycleData
import com.nik.assignment.ui.theme.*

@Composable
fun StabilitySummaryCard(
    stabilityPercentage: String,
    stabilityLabel: String,
    cycleData: List<CycleData>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(305.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Decorative blurred circle (top right)
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-10).dp, y = (-10).dp)
                    .background(
                        brush = Brush.radialGradient(
                            colors = listOf(GreenPrimary.copy(alpha = 0.35f), Color.Transparent),
                            radius = 150f
                        ),
                        shape = CircleShape
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 12.dp, end = 12.dp, top = 16.dp, bottom = 12.dp)
            ) {
                // Subtitle
                Text(
                    text = "Based on your recent logs and symptom patterns.",
                    style = InsightsTypography.subHeading3r,
                    color = TextSecondary,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Percentage block
                Column(
                    modifier = Modifier.width(142.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = stabilityLabel,
                        style = InsightsTypography.percentageLabel,
                        color = TextPrimary
                    )
                    Text(
                        text = stabilityPercentage,
                        style = InsightsTypography.percentageLarge,
                        color = TextPrimary
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                // Flow Graph
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .height(146.dp)) {
                    CycleFlowGraph(
                        cycleData = cycleData,
                        modifier = Modifier.fillMaxSize()
                    )
                    // Tooltip
                    Box(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .offset(x = 30.dp, y = 0.dp)
                    ) {
                        TooltipBubble(text = "28–32 days\n~4 months avg")
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                // X-axis dates
                CycleXAxisLabels(cycleData = cycleData)
            }
        }
    }
}

@Composable
private fun CycleFlowGraph(
    cycleData: List<CycleData>,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val width = size.width
        val height = size.height
        val minDays = 24f
        val maxDays = 34f

        // Background fill
        drawRoundRect(
            color = PurpleFaint,
            size = Size(width, height),
            cornerRadius = CornerRadius(4f)
        )

        // Inner bottom fill
        val innerTop = height * 0.60f
        drawRect(
            color = PurpleLight,
            topLeft = Offset(0f, innerTop),
            size = Size(width, height - innerTop)
        )

        if (cycleData.isNotEmpty()) {
            val step = width / (cycleData.size - 1).coerceAtLeast(1)
            val points = cycleData.mapIndexed { i, data ->
                val normalised = (data.cycleDays - minDays) / (maxDays - minDays)
                val y = height - (normalised * height * 0.85f) - height * 0.1f
                Offset(i * step, y)
            }

            // Area fill
            val path = Path()
            path.moveTo(points.first().x, height)
            points.forEach { path.lineTo(it.x, it.y) }
            path.lineTo(points.last().x, height)
            path.close()

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(PurpleDark.copy(alpha = 0.85f), PurpleDark.copy(alpha = 0.1f))
                )
            )

            // Line
            val linePath = Path()
            points.forEachIndexed { i, p ->
                if (i == 0) linePath.moveTo(p.x, p.y) else linePath.lineTo(p.x, p.y)
            }
            drawPath(linePath, color = PurpleMedium, style = Stroke(width = 2.dp.toPx()))

            // Dashed vertical indicator line
            val indicatorX = points.last().x
            val dashLength = 6.dp.toPx()
            val gapLength = 4.dp.toPx()
            var y = 0f
            while (y < height) {
                drawLine(
                    color = GreenPrimary,
                    start = Offset(indicatorX, y),
                    end = Offset(indicatorX, (y + dashLength).coerceAtMost(height)),
                    strokeWidth = 1.5.dp.toPx()
                )
                y += dashLength + gapLength
            }

            // Indicator dot
            val dotCenter = points.last()
            drawCircle(color = GreenPrimary, radius = 7.5.dp.toPx(), center = dotCenter)
            drawCircle(
                color = White,
                radius = 4.dp.toPx(),
                center = dotCenter,
                style = Stroke(2.dp.toPx())
            )
        }
    }
}

@Composable
private fun TooltipBubble(text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .background(Black, shape = RoundedCornerShape(8.dp))
                .padding(horizontal = 8.dp, vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                style = InsightsTypography.tooltipText,
                color = White,
                fontSize = 10.sp
            )
        }
        // Tooltip arrow
        Canvas(modifier = Modifier.size(width = 14.dp, height = 8.dp)) {
            val path = Path()
            path.moveTo(0f, 0f)
            path.lineTo(size.width, 0f)
            path.lineTo(size.width / 2, size.height)
            path.close()
            drawPath(path, color = Black)
        }
    }
}

@Composable
private fun CycleXAxisLabels(cycleData: List<CycleData>) {
    val months = listOf("Jan", "Feb", "Mar", "Apr")
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        months.forEach { month ->
            Text(
                text = month,
                style = InsightsTypography.cycleAxisLabel,
                color = if (month == "Mar") TextPrimary else TextSecondary,
                fontWeight = if (month == "Mar") androidx.compose.ui.text.font.FontWeight.Medium else null
            )
        }
    }
}
