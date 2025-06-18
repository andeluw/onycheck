package com.project.onycheck.ui.screens.doctors

import android.Manifest
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.project.onycheck.data.Doctor
import com.project.onycheck.ui.components.AnimatedCirclesLoader
import com.project.onycheck.ui.components.AppScaffold
import com.project.onycheck.ui.theme.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DoctorsScreen(
    navController: NavController,
    viewModel: DoctorsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // The permission state now calls the ViewModel when the result is known
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) { permissionResult ->
        viewModel.onPermissionResult(permissionResult.values.all { it })
    }

    // On first launch, request permissions
    LaunchedEffect(key1 = true) {
        if (!locationPermissions.allPermissionsGranted) {
            locationPermissions.launchMultiplePermissionRequest()
        } else {
            viewModel.onPermissionResult(true)
        }
    }

    AppScaffold(navController = navController) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 24.dp)
        ) {
            Column(modifier = Modifier.padding(start = 8.dp, bottom = 16.dp)) {
                if (!uiState.isLoading) {
                    Text(
                        "Find Doctors",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = Gray900
                    )
                }
                if (!uiState.locationPermissionGranted) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "Showing doctors in Surabaya. Allow location access for nearby results.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    AnimatedCirclesLoader(modifier = Modifier.size(56.dp))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Searching Doctors...",
                        style = MaterialTheme.typography.titleMedium,
                        color = Gray900
                    )
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.doctors) { doctor ->
                        DoctorCard(doctor = doctor)
                    }
                }
            }
        }
    }
}

@Composable
fun DoctorCard(doctor: Doctor) {
    val context = LocalContext.current
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = Blue50)
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                doctor.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = Gray900
            )
//            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                doctor.specialty,
//                style = MaterialTheme.typography.bodyMedium,
//                color = Blue800,
//                fontWeight = FontWeight.Medium
//            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(doctor.address, style = MaterialTheme.typography.bodyMedium)

            // ✅ Display the rating if available
            if (doctor.rating > 0) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFF59E0B) // Amber color for stars
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "${doctor.rating} (${doctor.totalRatings} reviews)",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = {
                        val gmmIntentUri =
                            Uri.parse("geo:${doctor.lat},${doctor.lon}?q=${Uri.encode(doctor.address)}")
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        context.startActivity(mapIntent)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Blue600,
                        contentColor = Color.White
                    )
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Maps",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Maps")
                }
                OutlinedButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${doctor.phone}"))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Blue700
                    ),
                    border = BorderStroke(1.5.dp, Blue700)
                ) {
                    Icon(
                        Icons.Default.Call,
                        contentDescription = "Call",
                        modifier = Modifier.size(ButtonDefaults.IconSize)
                    )
                    Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                    Text("Call")
                }
            }
        }
    }
}