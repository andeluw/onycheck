package com.project.onycheck.ui.screens.doctors

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.project.onycheck.data.Doctor
import com.project.onycheck.data.DoctorData
import com.project.onycheck.data.repository.PlacesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

data class DoctorsUiState(
    val doctors: List<Doctor> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null,
    val locationPermissionGranted: Boolean = false
)

@HiltViewModel
class DoctorsViewModel @Inject constructor(
    private val placesRepository: PlacesRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(DoctorsUiState())
    val uiState = _uiState.asStateFlow()

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    fun onPermissionResult(isGranted: Boolean) {
        _uiState.update { it.copy(locationPermissionGranted = isGranted) }
//        if (isGranted) {
//            fetchNearbyDoctors()
//        } else {
//            loadDefaultDoctors("Location permission denied. Showing results for Surabaya.")
//        }
        loadDefaultDoctors("Location permission denied. Showing results for Surabaya.")
    }

    @SuppressLint("MissingPermission")
    private fun fetchNearbyDoctors() {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            try {
                // Use .await() to get the location result in a coroutine
                val location: Location? = fusedLocationClient.lastLocation.await()

                if (location != null) {
                    val doctors = placesRepository.findNearbyDermatologists(
                        latitude = location.latitude,
                        longitude = location.longitude
                    )
                    _uiState.update { it.copy(doctors = doctors, isLoading = false) }
                } else {
                    loadDefaultDoctors("Could not retrieve current location. Showing default results.")
                }
            } catch (e: Exception) {
                loadDefaultDoctors("Failed to get location: ${e.message}")
            }
        }
    }

    private fun loadDefaultDoctors(errorMessage: String? = null) {
        _uiState.update {
            it.copy(
                isLoading = false,
                // The default list is doctors in Surabaya
                doctors = DoctorData.allDoctors.filter { doc -> doc.address.contains("Surabaya") },
                error = errorMessage
            )
        }
    }
}