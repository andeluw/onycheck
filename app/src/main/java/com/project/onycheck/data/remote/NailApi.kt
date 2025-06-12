package com.project.onycheck.data.remote

import com.project.onycheck.data.remote.dto.Base64ImagePayload
import com.project.onycheck.data.remote.dto.PredictionResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NailApi {
    @Multipart
    @POST("/predict")
    suspend fun predictNailCondition(
        @Part file: MultipartBody.Part
    ): Response<PredictionResponse>

    @POST("/predict_base64")
    suspend fun predictNailConditionBase64(
        @Body payload: Base64ImagePayload
    ): Response<PredictionResponse>
}