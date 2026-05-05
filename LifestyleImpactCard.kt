package com.nik.assignment.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nik.assignment.model.LifestyleColorType
import com.nik.assignment.model.LifestyleRow
import com.nik.assignment.ui.theme.*

@Composable
fun LifestyleImpactCard(
    lifestyleRows: List<LifestyleRow>,
    modifier: Modifier = Modifier
) {
    var selectedPeriod by remember { mutableStateOf("4 months") }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(192.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Correlation Strength",
                    style = InsightsTypography.subHeading2m,
                    color = TextPrimary
                )
                // Period dropdown chip
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(SurfaceSecondary)
                        .padding(horizontal = 4.dp, vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = selectedPeriod,
                        style = InsightsTypography.navigationText,
                        color = TextSecondary
                    )
                    // Chevron down icon (drawn via Canvas)
                    androidx.compose.foundation.Canvas(modifier = Modifier.size(16.dp)) {
                        val path = androidx.compose.ui.graphics.Path()
                        val cx = size.width / 2
                        val cy = size.height / 2
                        path.moveTo(cx - 4.dp.toPx(), cy - 2.dp.toPx())
                        path.lineTo(cx, cy + 2.dp.toPx())
                        path.lineTo(cx + 4.dp.toPx(), cy - 2.dp.toPx())
                        drawPath(
                            path,
                            color = TextSecondary,
                            style = androidx.compose.ui.graphics.drawscope.Stroke(
                                width = 1.dp.toPx(),
                                cap = androidx.compose.ui.graphics.StrokeCap.Round,
                                join = androidx.compose.ui.graphics.StrokeJoin.Round
                            )
                        )
                    }
                }
            }

            // Lifestyle rows
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                lifestyleRows.forEach { row ->
                    LifestyleCorrelationRow(row = row)
                }
            }
        }
    }
}

@Composable
private fun LifestyleCorrelationRow(row: LifestyleRow) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Label
        Text(
            text = row.label,
            style = InsightsTypography.chartLabel,
            color = TextPrimary,
            modifier = Modifier.width(38.dp)
        )

        // Bar segments
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(3.69.dp)
        ) {
            for (i in 0 until row.totalCount) {
                val isFilled = i < row.filledCount
                val brush = if (isFilled) getFilledBrush(row.colorType, i % 2 == 0) else null

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(22.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .then(
                            if (brush != null)
                                Modifier.background(brush)
                            else
                                Modifier.background(DividerColor)
                        )
                )
            }
        }
    }
}

private fun getFilledBrush(colorType: LifestyleColorType, forward: Boolean): Brush {
    return when (colorType) {
        LifestyleColorType.PURPLE -> Brush.horizontalGradient(
            colors = if (forward)
                listOf(PurpleLight, PurpleLight.copy(alpha = 0.34f))
            else
                listOf(PurpleLight.copy(alpha = 0.34f), PurpleLight)
        )
        LifestyleColorType.PINK -> Brush.horizontalGradient(
            colors = if (forward)
                listOf(PinkPrimary, PinkPrimary.copy(alpha = 0.56f))
            else
                listOf(PinkPrimary.copy(alpha = 0.56f), PinkPrimary)
        )
        LifestyleColorType.GREEN -> Brush.horizontalGradient(
            colors = if (forward)
                listOf(GreenPrimary, GreenPrimary.copy(alpha = 0.38f))
            else
                listOf(GreenPrimary.copy(alpha = 0.38f), GreenPrimary)
        )
        LifestyleColorType.LIGHT_PINK -> Brush.horizontalGradient(
            colors = if (forward)
                listOf(PinkSoft, PinkSoft.copy(alpha = 0.52f))
            else
                listOf(PinkSoft.copy(alpha = 0.52f), PinkSoft)
        )
    }
}
