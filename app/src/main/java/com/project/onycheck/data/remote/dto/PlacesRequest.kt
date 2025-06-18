package com.project.onycheck.data.remote.dto

data class PlacesRequest(
    val textQuery: String,
    val locationBias: LocationBias? = null,
    val rankPreference: String? = null
)

data class LocationBias(
    val circle: Circle
)

data class Circle(
    val center: Center,
    val radius: Double
)

data class Center(
    val latitude: Double,
    val longitude: Double
)