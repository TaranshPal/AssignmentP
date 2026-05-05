package com.nik.assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nik.assignment.ui.theme.InsightsTypography
import com.nik.assignment.ui.theme.TextPrimary
import java.util.Calendar

@Composable
fun StatusBar(modifier: Modifier = Modifier) {
    val calendar = Calendar.getInstance()
    val hours = String.format("%02d", calendar.get(Calendar.HOUR_OF_DAY))
    val minutes = String.format("%02d", calendar.get(Calendar.MINUTE))

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Time
        Text(
            text = "$hours:$minutes",
            style = InsightsTypography.navigationTextMedium.copy(
                fontSize = 14.sp,
                lineHeight = 17.sp
            ),
            color = TextPrimary
        )

        // Icons row (cellular, wifi, battery) — drawn with Canvas
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CellularSignalIcon()
            WifiIcon()
            BatteryIcon()
        }
    }
}

@Composable
private fun CellularSignalIcon() {
    Box(
        modifier = Modifier
            .width(18.dp)
            .height(10.dp)
            .drawBehind {
                drawCellularBars()
            }
    )
}

private fun DrawScope.drawCellularBars() {
    val barWidth = size.width / 6f
    val barGap = size.width / 6f

    // Bar 1 — 40% height
    drawRect(Color.Black, topLeft = Offset(0f, size.height * 0.6f), size = Size(barWidth, size.height * 0.4f))
    // Bar 2 — 60% height
    drawRect(Color.Black, topLeft = Offset(barWidth + barGap, size.height * 0.4f), size = Size(barWidth, size.height * 0.6f))
    // Bar 3 — 80% height
    drawRect(Color.Black, topLeft = Offset((barWidth + barGap) * 2, size.height * 0.2f), size = Size(barWidth, size.height * 0.8f))
    // Bar 4 — 100% height
    drawRect(Color.Black, topLeft = Offset((barWidth + barGap) * 3, 0f), size = Size(barWidth, size.height))
}

@Composable
private fun WifiIcon() {
    Box(
        modifier = Modifier
            .width(16.dp)
            .height(12.dp)
            .drawBehind {
                drawWifiBars()
            }
    )
}

private fun DrawScope.drawWifiBars() {
    val cx = size.width / 2f
    // Three arcs approximated as rounded rectangles at different heights
    val barW = size.width * 0.25f
    val barH = size.height * 0.12f

    // Dot
    drawRoundRect(
        Color.Black,
        topLeft = Offset(cx - barW / 2, size.height * 0.86f),
        size = Size(barW, barH * 1.2f),
        cornerRadius = CornerRadius(4f)
    )
    // Bar 2
    drawRoundRect(
        Color.Black,
        topLeft = Offset(cx - size.width * 0.25f, size.height * 0.5f),
        size = Size(size.width * 0.5f, barH),
        cornerRadius = CornerRadius(4f)
    )
    // Bar 3
    drawRoundRect(
        Color.Black,
        topLeft = Offset(0f, size.height * 0.15f),
        size = Size(size.width, barH),
        cornerRadius = CornerRadius(4f)
    )
}

@Composable
private fun BatteryIcon() {
    Box(
        modifier = Modifier
            .width(24.dp)
            .height(12.dp)
            .drawBehind {
                drawBattery()
            }
    )
}

private fun DrawScope.drawBattery() {
    val bodyWidth = size.width * 0.875f
    val bodyHeight = size.height

    // Border (battery body outline)
    drawRoundRect(
        Color.Black.copy(alpha = 0.4f),
        topLeft = Offset(0f, 0f),
        size = Size(bodyWidth, bodyHeight),
        cornerRadius = CornerRadius(4f)
    )

    // Fill indicator (~70% charged)
    drawRoundRect(
        Color.Black,
        topLeft = Offset(bodyWidth * 0.083f, bodyHeight * 0.167f),
        size = Size(bodyWidth * 0.7f, bodyHeight * 0.667f),
        cornerRadius = CornerRadius(2f)
    )

    // Cap (positive terminal)
    drawRoundRect(
        Color.Black.copy(alpha = 0.4f),
        topLeft = Offset(bodyWidth + 1.dp.toPx(), bodyHeight * 0.333f),
        size = Size(size.width * 0.083f, bodyHeight * 0.333f),
        cornerRadius = CornerRadius(2f)
    )
}
