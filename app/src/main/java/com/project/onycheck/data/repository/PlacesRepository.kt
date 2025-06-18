package com.project.onycheck.data.repository

import android.util.Log
import com.project.onycheck.BuildConfig
import com.project.onycheck.data.Doctor
import com.project.onycheck.data.remote.PlacesApi
import com.project.onycheck.data.remote.dto.Center
import com.project.onycheck.data.remote.dto.Circle
import com.project.onycheck.data.remote.dto.LocationBias
import com.project.onycheck.data.remote.dto.PlacesRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PlacesRepository @Inject constructor(
    private val placesApi: PlacesApi
) {
    suspend fun findNearbyDermatologists(
        latitude: Double,
        longitude: Double
    ): List<Doctor> {
        val request = PlacesRequest(
            textQuery = "dermatologist",
            locationBias = LocationBias(
                circle = Circle(
                    center = Center(latitude, longitude),
                    radius = 15000.0
                )
            ),
            rankPreference = "DISTANCE"
        )

        return try {
            val response = placesApi.findNearbyPlaces(
                apiKey = BuildConfig.MAPS_API_KEY,
                requestBody = request
            )

            Log.d("PlacesRepository", "API Response: ${response.body()}")

            if (response.isSuccessful && response.body() != null) {
                response.body()!!.places.map { placeResult ->
                    Doctor(
                        name = placeResult.displayName.text,
                        specialty = "Dermatologist",
                        address = placeResult.formattedAddress,
                        phone = placeResult.internationalPhoneNumber ?: "Not available",
                        lat = placeResult.location.latitude,
                        lon = placeResult.location.longitude,
                        rating = placeResult.rating ?: 0.0,
                        totalRatings = placeResult.userRatingCount ?: 0
                    )
                }
            } else {
                emptyList()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}