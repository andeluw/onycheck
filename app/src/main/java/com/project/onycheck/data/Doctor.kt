package com.project.onycheck.data

data class Doctor(
    val name: String,
    val specialty: String,
    val address: String,
    val phone: String,
    val lat: Double,
    val lon: Double,
    val rating: Double,
    val totalRatings: Int
)
