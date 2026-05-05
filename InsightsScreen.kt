package com.nik.assignment.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nik.assignment.ui.components.*
import com.nik.assignment.ui.theme.*
import com.nik.assignment.viewmodel.InsightsViewModel

@Composable
fun InsightsScreen(
    modifier: Modifier = Modifier,
    viewModel: InsightsViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    var selectedNavItem by remember { mutableStateOf(NavItem.INSIGHTS) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Background)
    ) {
        // Background decorative blur (top right)
        Box(
            modifier = Modifier
                .size(156.dp)
                .align(Alignment.TopEnd)
                .offset(x = (-27).dp, y = 85.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(PinkPrimary.copy(alpha = 0.65f), Color.Transparent),
                        radius = 250f
                    ),
                    shape = CircleShape
                )
        )

        Column(modifier = Modifier.fillMaxSize()) {
            // Status Bar
            StatusBar(modifier = Modifier.fillMaxWidth())

            // Top Bar: back arrow + title
            InsightsTopBar()

            // Scrollable content
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // ── Stability Summary ──
                SectionTitle(title = "Stability Summary")
                Spacer(modifier = Modifier.height(8.dp))
                StabilitySummaryCard(
                    stabilityPercentage = uiState.stabilityPercentage,
                    stabilityLabel = uiState.stabilityLabel,
                    cycleData = uiState.cycleData
                )

                Spacer(modifier = Modifier.height(16.dp))

                // ── Cycle Trends ──
                SectionTitle(title = "Cycle Trends")
                Spacer(modifier = Modifier.height(8.dp))
                CycleTrendsCard()

                Spacer(modifier = Modifier.height(16.dp))

                // ── Body & Metabolic Trends ──
                SectionTitle(title = "Body & Metabolic Trends")
                Spacer(modifier = Modifier.height(8.dp))
                BodyMetabolicCard(weightData = uiState.weightData)

                Spacer(modifier = Modifier.height(16.dp))

                // ── Body Signals ──
                SectionTitle(title = "Body Signals")
                Spacer(modifier = Modifier.height(8.dp))
                BodySignalsCard(bodySignals = uiState.bodySignals)

                Spacer(modifier = Modifier.height(16.dp))

                // ── Lifestyle Impact ──
                SectionTitle(title = "Lifestyle Impact")
                Spacer(modifier = Modifier.height(8.dp))
                LifestyleImpactCard(lifestyleRows = uiState.lifestyleRows)

                // Space for bottom nav
                Spacer(modifier = Modifier.height(80.dp))
            }
        }

        // ── Bottom Nav ──
        BottomNavBar(
            selectedItem = selectedNavItem,
            onItemSelected = { selectedNavItem = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        )

        // Home indicator bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
                .width(134.dp)
                .height(5.dp)
                .background(Color.Black, shape = CircleShape)
        )
    }
}

@Composable
private fun InsightsTopBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Back arrow (left)
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(24.dp)
        ) {
            BackArrowIcon()
        }

        // Title
        Text(
            text = "Insights",
            style = InsightsTypography.heading1s,
            color = TextPrimary,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun BackArrowIcon() {
    androidx.compose.foundation.Canvas(modifier = Modifier.size(24.dp)) {
        val cx = size.width / 2
        val cy = size.height / 2
        val path = androidx.compose.ui.graphics.Path()
        path.moveTo(cx + 4.dp.toPx(), cy - 5.dp.toPx())
        path.lineTo(cx - 2.dp.toPx(), cy)
        path.lineTo(cx + 4.dp.toPx(), cy + 5.dp.toPx())
        drawPath(
            path,
            color = Color(0xFFDCD9E7),
            style = androidx.compose.ui.graphics.drawscope.Stroke(
                width = 2.dp.toPx(),
                cap = androidx.compose.ui.graphics.StrokeCap.Round,
                join = androidx.compose.ui.graphics.StrokeJoin.Round
            )
        )
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = InsightsTypography.heading1s,
        color = TextPrimary
    )
}
