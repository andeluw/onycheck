package com.project.onycheck.data.remote.dto

import com.google.gson.annotations.SerializedName

// Top-level response
data class PlacesResponse(
    val places: List<PlaceResult> = emptyList()
)

// A single place result
data class PlaceResult(
    val displayName: DisplayName,
    val formattedAddress: String,
    val location: Location,
    val rating: Double?,
    val userRatingCount: Int?,
    val internationalPhoneNumber: String?
)

// The display name object
data class DisplayName(
    val text: String,
    val languageCode: String
)

// Location object (can be reused from PlacesRequest)
data class Location(
    val latitude: Double,
    val longitude: Double
)