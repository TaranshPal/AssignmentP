package com.nik.assignment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.nik.assignment.ui.screens.InsightsScreen
import com.nik.assignment.ui.theme.InsightsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            InsightsTheme {
                // InsightsScreen manages its own fillMaxSize internally
                InsightsScreen()
            }
        }
    }
}
