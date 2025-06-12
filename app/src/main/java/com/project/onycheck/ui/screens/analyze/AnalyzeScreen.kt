package com.project.onycheck.ui.screens.analyze

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.project.onycheck.R
import com.project.onycheck.data.remote.dto.PredictionResponse
import com.project.onycheck.ui.components.AppScaffold
import com.project.onycheck.ui.navigation.createDiseaseDetailRoute
import com.project.onycheck.ui.screens.analyze.components.CameraView
import com.project.onycheck.ui.screens.analyze.components.CustomImageCropper
import com.project.onycheck.ui.screens.analyze.components.DefaultAnalyzeContent
import com.project.onycheck.ui.screens.analyze.components.LoadingView
import com.project.onycheck.ui.screens.analyze.components.PhotoGuideDialog
import com.project.onycheck.ui.screens.analyze.components.ResultView
import com.project.onycheck.ui.screens.analyze.utils.saveBitmapToTempFile
import com.project.onycheck.ui.screens.analyze.utils.saveBitmapToUri
import com.project.onycheck.ui.screens.diseasedetail.DiseaseDetailScreen

private enum class AnalyzeScreenMode {
    DEFAULT,
    CAMERA,
    CROPPER
}

@Composable
fun AnalyzeScreen(
    navController: NavController,
    viewModel: AnalyzeViewModel = hiltViewModel()
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) }

    var hasCamPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var showCamera by remember { mutableStateOf(false) }

    var imageUriToCrop by remember { mutableStateOf<Uri?>(null) }

    var showGuideDialog by remember { mutableStateOf(false) }


    val isFullScreen = showCamera || imageUriToCrop != null

    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                imageUriToCrop = uri
                showCamera = false
            }
        }
    )

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            hasCamPermission = isGranted

            if (isGranted) {
                showCamera = true
            }
        }
    )


    val startAnalysis: () -> Unit = {
        if (hasCamPermission) {
            showCamera = true
        } else {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }


    // --- DEVELOPMENT SETUP ---

    val defaultImageUri = Uri.parse(
        "android.resource://${context.packageName}/${R.drawable.correctnail}"
    )


    val fakeLoadingState = uiState.copy(
        isLoading = true,
        selectedImageUri = defaultImageUri
    )


    val fakeHealthyState = uiState.copy(
        isLoading = false,
        selectedImageUri = defaultImageUri,
        predictionResult = PredictionResponse(predicted_class = "Healthy_Nail", confidence = 0.99),
        error = null
    )


    val fakeDiseaseState = uiState.copy(
        isLoading = false,
        selectedImageUri = defaultImageUri,
        predictionResult = PredictionResponse(
            predicted_class = "Acral_Lentiginous_Melanoma", confidence = 0.88
        ),
        error = null
    )


    val fakeFailedState = uiState.copy(
        isLoading = false,
        selectedImageUri = defaultImageUri,
        predictionResult = null,
        error = "Analysis failed due to poor lighting."
    )

    // --- END DEVELOPMENT SETUP ---


    if (isFullScreen) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (showCamera) {
                CameraView(
                    onImageCaptured = { uri ->
                        showCamera = false
                        imageUriToCrop = uri
                    },

                    onError = {
                        showCamera = false
                        imageUriToCrop = null
                        Log.e("AnalyzeScreen", "View error:", it)
                    },

                    onGalleryClick = {
                        photoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },

                    onCancel = {
                        showCamera = false
                    }
                )
            }
        }

        if (imageUriToCrop != null) {
            CustomImageCropper(
                uri = imageUriToCrop!!,
                onCropSuccess = { croppedBitmap ->
                    val uri = saveBitmapToTempFile(context, croppedBitmap)
                    viewModel.analyzeNailImage(context, uri)
                    imageUriToCrop = null
                    showCamera = false
                },


                onCropCancel = {
                    imageUriToCrop = null
                    showCamera = true
                }
            )
        }
    } else {
        AppScaffold(navController = navController) { innerPadding ->
            // --- DEVELOPMENT SETUP ---
            // Loading
//             LoadingView(uiState = fakeLoadingState, innerPadding = innerPadding)

            // Healthy Result
//            ResultView(
//                uiState = fakeHealthyState,
//                innerPadding = innerPadding,
//                onAnalyzeAnother = {},
//                onShowGuide = { showGuideDialog = true },
//                onLearnMore = { navController.navigate("news") },
//                onFindDoctor = { navController.navigate("doctors") },
//                onGoHome = { navController.navigate("home") }
//            )


            // Disease Result
//            ResultView(
//                uiState = fakeDiseaseState,
//                innerPadding = innerPadding,
//                onAnalyzeAnother = {},
//                onShowGuide = { showGuideDialog = true },
//                onLearnMore = { diseaseName ->
//                    navController.navigate(createDiseaseDetailRoute(diseaseName.toString()))
//                },
//                onFindDoctor = { navController.navigate("doctors") },
//                onGoHome = { navController.navigate("home") }
//            )


            // Failed Result
//            ResultView(
//                uiState = fakeFailedState,
//                innerPadding = innerPadding,
//                onAnalyzeAnother = {},
//                onShowGuide = { showGuideDialog = true },
//                onLearnMore = { diseaseName ->
//                    navController.navigate(createDiseaseDetailRoute(diseaseName.toString()))
//                },
//                onFindDoctor = { navController.navigate("doctors") },
//                onGoHome = { navController.navigate("home") }
//            )

            // Disease Detail
//            DiseaseDetailScreen(diseaseName = "Acral_Lentiginous_Melanoma", navController = navController)

            // --- END DEVELOPMENT SETUP ---

            when {
                uiState.isLoading -> LoadingView(uiState = uiState, innerPadding = innerPadding)
                uiState.predictionResult != null || uiState.error != null -> {
                    ResultView(
                        uiState = uiState,
                        innerPadding = innerPadding,
                        onAnalyzeAnother = {
                            viewModel.clearResult()
                            startAnalysis()
                        },
                        onShowGuide = { showGuideDialog = true },
                        onLearnMore = { diseaseName ->
                            navController.navigate(createDiseaseDetailRoute(diseaseName.toString()))
                        },
                        onFindDoctor = { navController.navigate("doctors") },
                        onGoHome = { navController.navigate("home") }
                    )
                }


                else -> {
                    DefaultAnalyzeContent(
                        innerPadding = innerPadding,
                        onAnalyzeClick = startAnalysis,
                        onShowGuide = { showGuideDialog = true }
                    )
                }
            }
        }
    }

    if (showGuideDialog) {
        PhotoGuideDialog(onDismiss = { showGuideDialog = false })
    }
}