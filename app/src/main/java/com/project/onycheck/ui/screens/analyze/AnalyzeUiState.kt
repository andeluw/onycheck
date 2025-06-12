package com.project.onycheck.ui.screens.analyze

import android.net.Uri
import com.project.onycheck.data.remote.dto.PredictionResponse

data class AnalyzeUiState(
    val isLoading: Boolean = false,
    val selectedImageUri: Uri? = null,
    val predictionResult: PredictionResponse? = null,
    val error: String? = null
)
