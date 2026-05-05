package com.nik.assignment.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nik.assignment.model.InsightsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class InsightsViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(InsightsUiState())
    val uiState: StateFlow<InsightsUiState> = _uiState.asStateFlow()

    fun onWeightFilterChanged(isMonthly: Boolean) {
        // Handle filter toggle if needed
    }

    fun onCorrelationPeriodChanged(months: Int) {
        // Handle period change if needed
    }
}
