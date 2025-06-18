package com.project.onycheck.data.remote

import com.project.onycheck.data.remote.dto.PlacesRequest
import com.project.onycheck.data.remote.dto.PlacesResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url

interface PlacesApi {
    @POST
    suspend fun findNearbyPlaces(
        @Url url: String = "https://places.googleapis.com/v1/places:searchText",
        @Header("X-Goog-Api-Key") apiKey: String,
        @Header("X-Goog-FieldMask") fields: String = "places.displayName,places.formattedAddress,places.location,places.rating,places.userRatingCount,places.nationalPhoneNumber",
        @Body requestBody: PlacesRequest
    ): Response<PlacesResponse>
}