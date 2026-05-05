package com.nik.assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.nik.assignment.model.WeightDataPoint
import com.nik.assignment.ui.theme.*

@Composable
fun BodyMetabolicCard(
    weightData: List<WeightDataPoint>,
    modifier: Modifier = Modifier
) {
    var isMonthlySelected by remember { mutableStateOf(true) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(266.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(0.dp)) {

            // Header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left: title + subtitle
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Your weight",
                        style = InsightsTypography.subHeading2m,
                        color = TextPrimary
                    )
                    Text(
                        text = "in kg",
                        style = InsightsTypography.bodyTextR,
                        color = TextSecondary
                    )
                }

                // Right: Monthly / Weekly toggle
                Row(
                    modifier = Modifier.height(32.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    FilterChip(
                        label = "Monthly",
                        isSelected = isMonthlySelected,
                        onClick = { isMonthlySelected = true }
                    )
                    FilterChip(
                        label = "Weekly",
                        isSelected = !isMonthlySelected,
                        onClick = { isMonthlySelected = false }
                    )
                }
            }

            // Y-axis labels + Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(bottom = 28.dp)
            ) {
                // Y-axis
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 22.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("75", style = InsightsTypography.chartLabel, color = TextPrimary)
                    Text("50", style = InsightsTypography.chartLabel, color = TextPrimary)
                    Text("25", style = InsightsTypography.chartLabel, color = TextPrimary)
                }

                // Chart area
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(start = 39.dp, end = 12.dp)
                ) {
                    WeightLineChart(
                        data = weightData,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                // X-axis labels
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxWidth()
                        .padding(start = 57.dp, end = 12.dp)
                        .offset(y = 22.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    weightData.forEach { point ->
                        Text(point.month, style = InsightsTypography.chartLabel, color = TextPrimary)
                    }
                }
            }
        }
    }
}

@Composable
private fun FilterChip(label: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(if (isSelected) Black else SurfaceSecondary)
            .clickable { onClick() }
            .padding(horizontal = 8.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            style = InsightsTypography.navigationTextMedium,
            color = if (isSelected) White else TextSecondary
        )
    }
}

@Composable
private fun WeightLineChart(
    data: List<WeightDataPoint>,
    modifier: Modifier = Modifier
) {
    androidx.compose.foundation.Canvas(modifier = modifier) {
        if (data.size < 2) return@Canvas

        val minY = 40f
        val maxY = 80f
        val step = size.width / (data.size - 1).coerceAtLeast(1)

        fun xOf(i: Int) = i * step
        fun yOf(v: Float) = size.height - ((v - minY) / (maxY - minY)) * size.height * 0.9f

        val points = data.mapIndexed { i, d -> Offset(xOf(i), yOf(d.weight)) }

        // Gradient fill
        val fillPath = Path()
        fillPath.moveTo(points.first().x, size.height)
        points.forEach { fillPath.lineTo(it.x, it.y) }
        fillPath.lineTo(points.last().x, size.height)
        fillPath.close()

        drawPath(
            fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(PinkPrimary.copy(alpha = 0.46f), PinkPrimary.copy(alpha = 0f))
            )
        )

        // Line
        val linePath = Path()
        points.forEachIndexed { i, p ->
            if (i == 0) linePath.moveTo(p.x, p.y) else linePath.lineTo(p.x, p.y)
        }
        drawPath(linePath, color = PinkPrimary, style = Stroke(width = 1.15.dp.toPx()))

        // Data point dots with glow
        points.forEach { p ->
            drawCircle(
                color = PinkPrimary.copy(alpha = 0.35f),
                radius = 8.dp.toPx(),
                center = p
            )
            drawCircle(color = White, radius = 4.dp.toPx(), center = p)
            drawCircle(color = PinkPrimary, radius = 2.dp.toPx(), center = p)
        }
    }
}
