package com.nik.assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import com.nik.assignment.ui.theme.*

enum class NavItem { HOME, TRACK, INSIGHTS, MORE }

@Composable
fun BottomNavBar(
    selectedItem: NavItem = NavItem.INSIGHTS,
    onItemSelected: (NavItem) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp), // gap between pill and More button
        verticalAlignment = Alignment.CenterVertically
    ) {
        // ── Main nav pill (Home, Track, Insights) ──────────────────────────
        // FIX: weight(1f) makes the pill fill all remaining space after the
        // More button (60dp) + gap (12dp), so the three items inside it are
        // evenly distributed and the whole bar looks centred.
        Row(
            modifier = Modifier
                .weight(1f)                          // ← fills remaining width
                .clip(RoundedCornerShape(100.dp))
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(White.copy(alpha = 0.2f), White.copy(alpha = 0.2f))
                    )
                )
                .glassMorphism()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly  // ← centres items inside pill
        ) {
            NavPillItem(
                label = "Home",
                isSelected = selectedItem == NavItem.HOME,
                iconContent = { HomeIcon(isSelected = selectedItem == NavItem.HOME) },
                onClick = { onItemSelected(NavItem.HOME) }
            )
            NavPillItem(
                label = "Track",
                isSelected = selectedItem == NavItem.TRACK,
                iconContent = { TrackIcon(isSelected = selectedItem == NavItem.TRACK) },
                onClick = { onItemSelected(NavItem.TRACK) }
            )
            NavPillItem(
                label = "Insights",
                isSelected = selectedItem == NavItem.INSIGHTS,
                iconContent = { InsightsNavIcon(isSelected = selectedItem == NavItem.INSIGHTS) },
                onClick = { onItemSelected(NavItem.INSIGHTS) }
            )
        }

        // ── "More" circular button ─────────────────────────────────────────
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(White.copy(alpha = 0.2f))
                .glassMorphism()
                .clickable { onItemSelected(NavItem.MORE) },
            contentAlignment = Alignment.Center
        ) {
            MoreIcon()
        }
    }
}

fun Modifier.glassMorphism(): Modifier = this.drawBehind {
    drawIntoCanvas { canvas ->
        val paint = Paint().apply {
            asFrameworkPaint().apply {
                isAntiAlias = true
                color = android.graphics.Color.TRANSPARENT
            }
        }
        canvas.drawRoundRect(
            left = 0f,
            top = 0f,
            right = size.width,
            bottom = size.height,
            radiusX = 100.dp.toPx(),
            radiusY = 100.dp.toPx(),
            paint = paint
        )
    }
}

@Composable
private fun NavPillItem(
    label: String,
    isSelected: Boolean,
    iconContent: @Composable () -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 9.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(modifier = Modifier.size(24.dp)) { iconContent() }
        Text(
            text = label,
            style = InsightsTypography.navigationText,
            color = if (isSelected) TextPrimary else TextPrimary.copy(alpha = 0.4f)
        )
    }
}

@Composable
private fun HomeIcon(isSelected: Boolean) {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val alpha = if (isSelected) 1f else 0.4f
        val path = Path()
        path.moveTo(size.width * 0.5f, size.height * 0.1f)
        path.lineTo(size.width * 0.9f, size.height * 0.45f)
        path.lineTo(size.width * 0.85f, size.height * 0.45f)
        path.lineTo(size.width * 0.85f, size.height * 0.9f)
        path.lineTo(size.width * 0.15f, size.height * 0.9f)
        path.lineTo(size.width * 0.15f, size.height * 0.45f)
        path.lineTo(size.width * 0.1f, size.height * 0.45f)
        path.close()
        drawPath(path, color = Color.Black.copy(alpha = alpha), style = Stroke(width = 1.5.dp.toPx()))
    }
}

@Composable
private fun TrackIcon(isSelected: Boolean) {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val alpha = if (isSelected) 1f else 0.4f
        drawRoundRect(
            color = Color.Black.copy(alpha = alpha),
            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4.dp.toPx()),
            style = Stroke(width = 1.5.dp.toPx())
        )
        drawLine(
            color = Color.Black.copy(alpha = alpha),
            start = androidx.compose.ui.geometry.Offset(size.width * 0.3f, size.height * 0.05f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.3f, size.height * 0.35f),
            strokeWidth = 1.5.dp.toPx()
        )
        drawLine(
            color = Color.Black.copy(alpha = alpha),
            start = androidx.compose.ui.geometry.Offset(size.width * 0.7f, size.height * 0.05f),
            end = androidx.compose.ui.geometry.Offset(size.width * 0.7f, size.height * 0.35f),
            strokeWidth = 1.5.dp.toPx()
        )
        drawLine(
            color = Color.Black.copy(alpha = alpha),
            start = androidx.compose.ui.geometry.Offset(0f, size.height * 0.45f),
            end = androidx.compose.ui.geometry.Offset(size.width, size.height * 0.45f),
            strokeWidth = 1.dp.toPx()
        )
    }
}

@Composable
private fun InsightsNavIcon(isSelected: Boolean) {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val barW = size.width * 0.15f
        val spacing = size.width * 0.1f
        val heights = listOf(0.6f, 0.9f, 0.4f, 0.75f)
        heights.forEachIndexed { i, h ->
            val x = i * (barW + spacing) + spacing
            val barH = size.height * h
            drawRoundRect(
                color = Color.Black,
                topLeft = androidx.compose.ui.geometry.Offset(x, size.height - barH),
                size = androidx.compose.ui.geometry.Size(barW, barH),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(2.dp.toPx())
            )
        }
    }
}

@Composable
private fun MoreIcon() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val cx = size.width / 2
        val cy = size.height / 2
        drawCircle(Color.Black.copy(alpha = 0.4f), radius = 2.dp.toPx(), center = androidx.compose.ui.geometry.Offset(cx - 6.dp.toPx(), cy))
        drawCircle(Color.Black.copy(alpha = 0.4f), radius = 2.dp.toPx(), center = androidx.compose.ui.geometry.Offset(cx, cy))
        drawCircle(Color.Black.copy(alpha = 0.4f), radius = 2.dp.toPx(), center = androidx.compose.ui.geometry.Offset(cx + 6.dp.toPx(), cy))
    }
}