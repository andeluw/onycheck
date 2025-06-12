package com.project.onycheck.ui.screens.analyze

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.project.onycheck.data.remote.dto.ApiError
import com.project.onycheck.data.repository.NailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class AnalyzeViewModel @Inject constructor(
    private val nailRepository: NailRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(AnalyzeUiState())
    val uiState = _uiState.asStateFlow()

    fun analyzeNailImage(context: Context, imageUri: Uri) {
        _uiState.update { it.copy(selectedImageUri = imageUri) }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                val multipartBody = createMultipartBody(context, imageUri)
                val response = nailRepository.analyzeNailImage(multipartBody)

                if (response.isSuccessful && response.body() != null) {
                    Log.d("AnalyzeViewModel", "API Success: ${response.body()}")

                    _uiState.update {
                        it.copy(isLoading = false, predictionResult = response.body())
                    }
                } else {
                    val errorMsg = parseError(response)
                    Log.e("AnalyzeViewModel", "API Error: $errorMsg")

                    _uiState.update {
                        it.copy(isLoading = false, error = errorMsg)
                    }
                }
            } catch (e: Exception) {
                Log.e("AnalyzeViewModel", "Network Exception: ${e.message}", e)

                _uiState.update {
                    it.copy(isLoading = false, error = "Network Error: ${e.message}")
                }
            }
        }
    }

    fun clearResult() {
        _uiState.update {
            it.copy(
                predictionResult = null,
                error = null
            )
        }
    }

    private fun parseError(response: Response<*>): String {
        return try {
            val errorBody = response.errorBody()?.string()
            val apiError = Gson().fromJson(errorBody, ApiError::class.java)
            apiError.error ?: "An unknown API error occurred."
        } catch (e: Exception) {
            "API Error (${response.code()}): ${response.message()}"
        }
    }

    private fun createMultipartBody(context: Context, uri: Uri): MultipartBody.Part {
        val stream = context.contentResolver.openInputStream(uri)!!
        val requestBody = stream.readBytes().toRequestBody("image/jpeg".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", "nail_image.jpg", requestBody)
    }
}