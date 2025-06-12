package com.project.onycheck.data.remote.dto

data class PredictionResponse(
    val predicted_class: String,
    val confidence: Double
)
