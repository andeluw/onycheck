package com.project.onycheck.data.repository

import com.project.onycheck.data.remote.NailApi
import com.project.onycheck.data.remote.dto.Base64ImagePayload
import okhttp3.MultipartBody
import javax.inject.Inject

class NailRepository @Inject constructor(private val api: NailApi) {
    suspend fun analyzeNailImage(file: MultipartBody.Part) = api.predictNailCondition(file)

    suspend fun analyzeNailImageBase64(base64Image: String) =
        api.predictNailConditionBase64(Base64ImagePayload(image_base64 = base64Image))
}