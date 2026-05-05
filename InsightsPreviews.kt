package com.nik.assignment.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.nik.assignment.model.*
import com.nik.assignment.ui.components.*
import com.nik.assignment.ui.theme.InsightsTheme

// ─── Full Screen Preview ───────────────────────────────────────────
@Preview(
    name = "Insights Screen – Pixel 6",
    showBackground = true,
    widthDp = 393,
    heightDp = 851
)
@Composable
fun InsightsScreenPreview() {
    InsightsTheme {
        InsightsScreen()
    }
}

@Preview(
    name = "Insights Screen – Small (375×812)",
    showBackground = true,
    widthDp = 375,
    heightDp = 812
)
@Composable
fun InsightsScreenSmallPreview() {
    InsightsTheme {
        InsightsScreen()
    }
}

// ─── Individual Card Previews ──────────────────────────────────────

@Preview(name = "Stability Summary Card", showBackground = true, widthDp = 375)
@Composable
fun StabilitySummaryCardPreview() {
    InsightsTheme {
        StabilitySummaryCard(
            stabilityPercentage = "76%",
            stabilityLabel = "Cycle Stability",
            cycleData = listOf(
                CycleData("Jan", 28),
                CycleData("Feb", 30),
                CycleData("Mar", 28, true),
                CycleData("Apr", 32),
                CycleData("May", 28),
                CycleData("Jun", 28)
            )
        )
    }
}

@Preview(name = "Cycle Trends Card", showBackground = true, widthDp = 375)
@Composable
fun CycleTrendsCardPreview() {
    InsightsTheme {
        CycleTrendsCard()
    }
}

@Preview(name = "Body Metabolic Card", showBackground = true, widthDp = 375)
@Composable
fun BodyMetabolicCardPreview() {
    InsightsTheme {
        BodyMetabolicCard(
            weightData = listOf(
                WeightDataPoint("Jan", 62f),
                WeightDataPoint("Feb", 58f),
                WeightDataPoint("Mar", 60f),
                WeightDataPoint("Apr", 50f),
                WeightDataPoint("May", 55f)
            )
        )
    }
}

@Preview(name = "Body Signals Card", showBackground = true, widthDp = 375)
@Composable
fun BodySignalsCardPreview() {
    InsightsTheme {
        BodySignalsCard(
            bodySignals = listOf(
                BodySignalData("Cramps", 72f, SignalColorType.PURPLE),
                BodySignalData("Bloating", 45f, SignalColorType.PINK),
                BodySignalData("Fatigue", 60f, SignalColorType.GREEN),
                BodySignalData("Mood", 38f, SignalColorType.LIGHT_PINK)
            )
        )
    }
}

@Preview(name = "Lifestyle Impact Card", showBackground = true, widthDp = 375)
@Composable
fun LifestyleImpactCardPreview() {
    InsightsTheme {
        LifestyleImpactCard(
            lifestyleRows = listOf(
                LifestyleRow("Sleep", 7, colorType = LifestyleColorType.PURPLE),
                LifestyleRow("Hydrate", 3, colorType = LifestyleColorType.PINK),
                LifestyleRow("Caffeine", 5, colorType = LifestyleColorType.GREEN),
                LifestyleRow("Exercise", 4, colorType = LifestyleColorType.LIGHT_PINK)
            )
        )
    }
}

@Preview(name = "Bottom Nav Bar", showBackground = true, widthDp = 375)
@Composable
fun BottomNavBarPreview() {
    InsightsTheme {
        BottomNavBar(selectedItem = NavItem.INSIGHTS)
    }
}

@Preview(name = "Status Bar", showBackground = true, widthDp = 375)
@Composable
fun StatusBarPreview() {
    InsightsTheme {
        StatusBar()
    }
}
