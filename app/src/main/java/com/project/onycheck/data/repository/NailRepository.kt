package com.project.onycheck.data.repository

import com.project.onycheck.data.remote.NailApi
import okhttp3.MultipartBody
import javax.inject.Inject

class NailRepository @Inject constructor(private val api: NailApi) {
    suspend fun analyzeNailImage(file: MultipartBody.Part) = api.predictNailCondition(file)
}