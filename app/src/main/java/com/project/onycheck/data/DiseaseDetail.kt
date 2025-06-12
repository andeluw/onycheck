package com.project.onycheck.data

import androidx.annotation.DrawableRes

data class DiseaseDetail(
    val key: String,
    val userFriendlyName: String,
    @DrawableRes val imageRes: Int,
    val overview: String,
    val symptoms: List<String>,
    val causes: List<String>,
    val nextSteps: List<String>
)