package com.nik.assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nik.assignment.ui.theme.*

// Each bar has 3 stacked segments: main (purple), period (pink), fertile (green)
data class CycleBarData(
    val month: String,
    val cycleDays: Int,
    val totalBarHeight: Int, // in dp
    val periodSegmentHeight: Int = 34, // fixed from spec
    val fertileSegmentHeight: Int = 34, // fixed from spec
    val numberTop: Int   // offset for the day number label
)

@Composable
fun CycleTrendsCard(
    modifier: Modifier = Modifier
) {
    val bars = listOf(
        CycleBarData("Jan", 28, 154, numberTop = 31),
        CycleBarData("Feb", 30, 166, numberTop = 16),
        CycleBarData("Mar", 28, 155, numberTop = 31),
        CycleBarData("Apr", 32, 166, numberTop = 20),
        CycleBarData("May", 28, 155, numberTop = 31),
        CycleBarData("Jun", 28, 154, numberTop = 31)
    )

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(237.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            // Chart area with bars
            Box(
                modifier = Modifier
                    .width(280.dp)
                    .height(155.dp)
                    .align(Alignment.Center)
                    .offset(y = (-10).dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.Bottom
                ) {
                    bars.forEach { bar ->
                        CycleBar(bar = bar)
                    }
                }
            }

            // Navigation arrows
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterStart)
                    .offset(x = 10.dp)
            ) {
                NavigationArrowLeft()
            }
            Box(
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.CenterEnd)
                    .offset(x = (-10).dp)
            ) {
                NavigationArrowRight()
            }

            // X-axis month labels
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 45.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                bars.forEach { bar ->
                    Text(
                        text = bar.month,
                        style = InsightsTypography.chartLabel,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}

@Composable
private fun CycleBar(bar: CycleBarData) {
    val maxHeight = 166.dp
    val barHeight = bar.totalBarHeight.dp

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.height(maxHeight + 16.dp)
    ) {
        Text(
            text = bar.cycleDays.toString(),
            style = InsightsTypography.chartBarLabel,
            color = TextPrimary
        )
        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .width(13.dp)
                .height(barHeight)
                .clip(RoundedCornerShape(12.dp))
                .background(PurpleLight)
        ) {
            // Green (fertile) segment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bar.fertileSegmentHeight.dp)
                    .align(Alignment.TopCenter)
                    .offset(y = (barHeight * 0.3f))
                    .background(GreenPrimary)
            )
            // Pink (period) segment
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(bar.periodSegmentHeight.dp)
                    .align(Alignment.BottomCenter)
                    .background(PinkPrimary)
            )
        }
    }
}

@Composable
private fun NavigationArrowLeft() {
    Box(
        modifier = Modifier
            .size(18.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2
            val cy = size.height / 2
            val r = size.width / 2 - 1.dp.toPx()
            drawCircle(
                color = PurpleLight,
                radius = r,
                center = androidx.compose.ui.geometry.Offset(cx, cy),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.13.dp.toPx())
            )
            val path = androidx.compose.ui.graphics.Path()
            path.moveTo(cx + r * 0.3f, cy - r * 0.35f)
            path.lineTo(cx - r * 0.15f, cy)
            path.lineTo(cx + r * 0.3f, cy + r * 0.35f)
            drawPath(
                path = path,
                color = PurpleLight,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 1.13.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round,
                    join = androidx.compose.ui.graphics.StrokeJoin.Round
                )
            )
        }
    }
}

@Composable
private fun NavigationArrowRight() {
    Box(
        modifier = Modifier
            .size(18.dp)
            .background(Color.Transparent),
        contentAlignment = Alignment.Center
    ) {
        androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxSize()) {
            val cx = size.width / 2
            val cy = size.height / 2
            val r = size.width / 2 - 1.dp.toPx()
            drawCircle(
                color = PurpleLight,
                radius = r,
                center = androidx.compose.ui.geometry.Offset(cx, cy),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = 1.13.dp.toPx())
            )
            val path = androidx.compose.ui.graphics.Path()
            path.moveTo(cx - r * 0.3f, cy - r * 0.35f)
            path.lineTo(cx + r * 0.15f, cy)
            path.lineTo(cx - r * 0.3f, cy + r * 0.35f)
            drawPath(
                path = path,
                color = PurpleLight,
                style = androidx.compose.ui.graphics.drawscope.Stroke(
                    width = 1.13.dp.toPx(),
                    cap = androidx.compose.ui.graphics.StrokeCap.Round,
                    join = androidx.compose.ui.graphics.StrokeJoin.Round
                )
            )
        }
    }
}
