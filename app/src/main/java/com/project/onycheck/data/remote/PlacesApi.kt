package com.project.onycheck.data.remote

import com.project.onycheck.data.remote.dto.PlacesRequest
import com.project.onycheck.data.remote.dto.PlacesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface PlacesApi {
    /**
     * Performs a Text Search using the new Google Places v1 API.
     * @param apiKey Your Google Maps API Key.
     * @param requestBody The JSON body for the POST request.
     * @return A Response containing a list of nearby places.
     */
    @POST
    suspend fun findNearbyPlaces(
        // The URL is now passed directly here because the base URL might differ
        @Url url: String = "https://places.googleapis.com/v1/places:searchText",
        @Header("X-Goog-Api-Key") apiKey: String,
        // ✅ The FieldMask is crucial. It specifies which data fields you want back.
        @Header("X-Goog-FieldMask") fields: String = "places.displayName,places.formattedAddress,places.location,places.rating,places.userRatingCount,places.internationalPhoneNumber",
        @Body requestBody: PlacesRequest
    ): Response<PlacesResponse>
}