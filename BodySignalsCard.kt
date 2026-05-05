package com.nik.assignment.ui.components

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.dp
import com.nik.assignment.model.BodySignalData
import com.nik.assignment.model.SignalColorType
import com.nik.assignment.ui.theme.*

@Composable
fun BodySignalsCard(
    bodySignals: List<BodySignalData>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(376.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Header
            Column(
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 12.dp, top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = "Symptom Card",
                    style = InsightsTypography.heading1s,
                    color = TextPrimary
                )
                Text(
                    text = "Compared to last cycle",
                    style = InsightsTypography.subHeading3r,
                    color = TextSecondary
                )
            }

            // Pie chart with floating labels
            Box(
                modifier = Modifier
                    .size(282.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = (-8).dp)
            ) {
                PieChartWithLabels(signals = bodySignals)
            }
        }
    }
}

@Composable
private fun PieChartWithLabels(signals: List<BodySignalData>) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(
            modifier = Modifier
                .size(272.dp)
                .align(Alignment.Center)
        ) {
            val total = signals.sumOf { it.percentage.toDouble() }.toFloat()
            var startAngle = -90f
            val outerR = size.minDimension / 2f
            val innerR = outerR * 0.45f

            signals.forEach { signal ->
                val sweep = (signal.percentage / total) * 360f
                val brush = when (signal.colorType) {
                    SignalColorType.PURPLE -> Brush.linearGradient(
                        colors = listOf(PurpleLight, PurpleBackground),
                        start = Offset(0f, 0f),
                        end = Offset(size.width, size.height)
                    )
                    SignalColorType.PINK -> Brush.linearGradient(
                        colors = listOf(PinkPrimary, PinkFaint),
                        start = Offset(size.width, 0f),
                        end = Offset(0f, size.height)
                    )
                    SignalColorType.GREEN -> Brush.linearGradient(
                        colors = listOf(GreenFaint, GreenPrimary),
                        start = Offset(0f, size.height),
                        end = Offset(size.width, 0f)
                    )
                    SignalColorType.LIGHT_PINK -> Brush.linearGradient(
                        colors = listOf(PinkBackground, PinkLight),
                        start = Offset(size.width / 2, size.height),
                        end = Offset(size.width / 2, 0f)
                    )
                }

                drawArc(
                    brush = brush,
                    startAngle = startAngle,
                    sweepAngle = sweep,
                    useCenter = true,
                    size = size
                )

                startAngle += sweep
            }

            // Inner white circle (donut hole)
            drawCircle(color = White, radius = innerR)
        }

        // Floating label bubbles at cardinal positions
        val labelPositions = listOf(
            Offset(0.88f, 0.25f) to Pair("30%", "Cramps"),
            Offset(0.65f, 0.88f) to Pair("25%", "Bloating"),
            Offset(0.08f, 0.75f) to Pair("20%", "Fatigue"),
            Offset(0.12f, 0.28f) to Pair("35%", "Mood")
        )

        labelPositions.forEach { (normOffset, label) ->
            PieLabelBubble(
                percentage = label.first,
                name = label.second,
                modifier = Modifier
                    .offset(
                        x = (normOffset.x * 282 - 30).dp,
                        y = (normOffset.y * 282 - 30).dp
                    )
            )
        }
    }
}

@Composable
private fun PieLabelBubble(
    percentage: String,
    name: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .size(60.dp)
            .shadow(elevation = 8.dp, shape = CircleShape)
            .clip(CircleShape)
            .background(White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = percentage,
            style = InsightsTypography.pieLabel,
            color = TextPrimary
        )
        Text(
            text = name,
            style = InsightsTypography.pieLabelSub,
            color = TextPrimary
        )
    }
}
