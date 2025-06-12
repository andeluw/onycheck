package com.project.onycheck.ui.screens.analyze.components

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.Camera
import androidx.camera.core.CameraSelector
import androidx.camera.core.FocusMeteringAction
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.FlashAuto
import androidx.compose.material.icons.rounded.FlashOff
import androidx.compose.material.icons.rounded.FlashOn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.AsyncImage
import com.composables.icons.lucide.Image
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.RefreshCw
import com.composables.icons.lucide.X
import com.project.onycheck.ui.screens.analyze.utils.getNewImageUri
import com.project.onycheck.ui.screens.analyze.utils.saveBitmapToUri
import com.project.onycheck.ui.screens.analyze.utils.takePhoto
import com.smarttoolfactory.cropper.ImageCropper
import com.smarttoolfactory.cropper.model.OutlineType
import com.smarttoolfactory.cropper.model.RectCropShape
import com.smarttoolfactory.cropper.settings.CropDefaults
import com.smarttoolfactory.cropper.settings.CropOutlineProperty
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit
import kotlin.math.roundToInt

@Composable
internal fun CustomImageCropper(
    uri: Uri,
    onCropSuccess: (Bitmap) -> Unit,
//    onCropSuccess: (Uri) -> Unit,
    onCropCancel: () -> Unit
) {
    val context = LocalContext.current
    var crop by remember { mutableStateOf(false) }
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(uri) {
        imageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ImageDecoder.decodeBitmap(ImageDecoder.createSource(context.contentResolver, uri))
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        }.asImageBitmap()
    }

    val handleSize: Float = LocalDensity.current.run { 20.dp.toPx() }
    val cropProperties by remember {
        mutableStateOf(
            CropDefaults.properties(
                handleSize = handleSize,
                cropOutlineProperty = CropOutlineProperty(
                    OutlineType.Rect,
                    RectCropShape(0, "Rect")
                )
            )
        )
    }
    val cropStyle by remember { mutableStateOf(CropDefaults.style()) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (imageBitmap != null) {
            ImageCropper(
                modifier = Modifier.fillMaxSize(),
                imageBitmap = imageBitmap!!,
                contentDescription = "Image to crop",
                cropStyle = cropStyle,
                cropProperties = cropProperties,
                crop = crop,
                onCropStart = {},
                onCropSuccess = { onCropSuccess(it.asAndroidBitmap()) }
//                onCropSuccess = { croppedBitmap ->
//                    val finalUri = saveBitmapToUri(context, croppedBitmap.asAndroidBitmap())
//                    onCropSuccess(finalUri)
//                }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Color.Black.copy(alpha = 0.8f))
                    .padding(vertical = 24.dp, horizontal = 16.dp)
                    .padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onCropCancel) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Cancel Crop",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
                IconButton(onClick = { crop = true }) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Confirm Crop",
                        tint = Color.White,
                        modifier = Modifier.size(36.dp)
                    )
                }
            }
        } else {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
internal fun CameraView(
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit,
    onGalleryClick: () -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var flashMode by remember { mutableIntStateOf(ImageCapture.FLASH_MODE_OFF) }

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    var cameraSelector by remember { mutableStateOf(CameraSelector.DEFAULT_BACK_CAMERA) }
    val previewView = remember { PreviewView(context) }
    var camera: Camera? by remember { mutableStateOf(null) }
    var tapOffset by remember { mutableStateOf<Offset?>(null) }
    var hasFlash by remember { mutableStateOf(false) }

    LaunchedEffect(flashMode) {
        camera?.let {
            if (it.cameraInfo.hasFlashUnit()) {
                it.cameraControl.enableTorch(flashMode == ImageCapture.FLASH_MODE_ON)
            }
        }
        imageCapture.flashMode = flashMode
    }

    LaunchedEffect(tapOffset) {
        if (tapOffset != null) {
            delay(1750L)
            tapOffset = null
        }
    }

    LaunchedEffect(cameraSelector) {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(previewView.surfaceProvider)
            }
            try {
                cameraProvider.unbindAll()
                camera = cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    cameraSelector,
                    preview,
                    imageCapture
                )
                hasFlash = camera?.cameraInfo?.hasFlashUnit() == true
            } catch (exc: Exception) {
                Log.e("CameraView", "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(context))
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { previewView },
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        tapOffset = offset
                        camera?.let {
                            val factory = previewView.meteringPointFactory
                            val point = factory.createPoint(offset.x, offset.y)
                            val action = FocusMeteringAction
                                .Builder(point, FocusMeteringAction.FLAG_AF)
                                .setAutoCancelDuration(5, TimeUnit.SECONDS)
                                .build()
                            it.cameraControl.startFocusAndMetering(action)
                        }
                    }
                }
        )

        AnimatedVisibility(
            visible = tapOffset != null,
            enter = fadeIn() + scaleIn(initialScale = 1.3f),
            exit = fadeOut()
        ) {
            tapOffset?.let {
                Box(
                    modifier = Modifier
                        .offset {
                            IntOffset(
                                x = it.x.roundToInt() - 30.dp.toPx().roundToInt(),
                                y = it.y.roundToInt() - 30.dp.toPx().roundToInt()
                            )
                        }
                        .size(60.dp)
                        .border(1.5.dp, Color.White, CircleShape)
                )
            }
        }

        IconButton(
            onClick = onCancel,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(16.dp)
                .padding(top = 12.dp)
        ) {
            Icon(Lucide.X, contentDescription = "Close Camera", tint = Color.White)
        }

        if (hasFlash) {
            IconButton(
                onClick = {
                    flashMode = when (flashMode) {
                        ImageCapture.FLASH_MODE_OFF -> ImageCapture.FLASH_MODE_ON
                        ImageCapture.FLASH_MODE_ON -> ImageCapture.FLASH_MODE_AUTO
                        else -> ImageCapture.FLASH_MODE_OFF
                    }
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .padding(top = 12.dp)
            ) {
                val icon = when (flashMode) {
                    ImageCapture.FLASH_MODE_ON -> Icons.Rounded.FlashOn
                    ImageCapture.FLASH_MODE_AUTO -> Icons.Rounded.FlashAuto
                    else -> Icons.Rounded.FlashOff
                }
                val description = "Flash: " + when (flashMode) {
                    ImageCapture.FLASH_MODE_ON -> "On"
                    ImageCapture.FLASH_MODE_AUTO -> "Auto"
                    else -> "Off"
                }
                Icon(imageVector = icon, contentDescription = description, tint = Color.White)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .background(Color(0x80161616))
                .padding(bottom = 48.dp, top = 28.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            LatestGalleryImageButton(
                modifier = Modifier.size(48.dp),
                onClick = onGalleryClick
            )
            IconButton(
                modifier = Modifier
                    .size(64.dp)
                    .border(4.dp, Color.White, CircleShape),
                onClick = {
                    takePhoto(context, imageCapture, onImageCaptured, onError)
                }
//                onClick = {
//                    val uri = getNewImageUri(context)
//                    val outputOptions = ImageCapture.OutputFileOptions.Builder(
//                        context.contentResolver,
//                        uri,
//                        ContentValues()
//                    ).build()
//
//                    imageCapture.takePicture(
//                        outputOptions,
//                        ContextCompat.getMainExecutor(context),
//                        object : ImageCapture.OnImageSavedCallback {
//                            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                                onImageCaptured(uri)
//                            }
//                            override fun onError(exc: ImageCaptureException) {
//                                onError(exc)
//                            }
//                        }
//                    )
//                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .background(color = Color.White, shape = CircleShape)
                        .clip(CircleShape)
                )
            }
            IconButton(onClick = {
                cameraSelector = if (cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                    CameraSelector.DEFAULT_FRONT_CAMERA
                } else {
                    CameraSelector.DEFAULT_BACK_CAMERA
                }
            }, modifier = Modifier.size(36.dp)) {
                Icon(
                    imageVector = Lucide.RefreshCw,
                    contentDescription = "Switch Camera",
                    tint = Color.White,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}


@Composable
fun LatestGalleryImageButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var isLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val projection = arrayOf(MediaStore.Images.Media._ID)
        val sortOrder = "${MediaStore.Images.Media.DATE_TAKEN} DESC"

        try {
            context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )?.use { cursor ->
                if (cursor.moveToFirst()) {
                    val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                    val id = cursor.getLong(idColumn)
                    imageUri = Uri.withAppendedPath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        id.toString()
                    )
                }
            }
        } catch (e: Exception) {
            Log.e("GalleryButton", "Failed to load latest image", e)
        } finally {
            isLoading = false
        }
    }

    IconButton(
        onClick = onClick, modifier = modifier
    ) {
        if (imageUri != null) {
            AsyncImage(
                model = imageUri,
                contentDescription = "Open Gallery",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .border(2.dp, Color.White, RoundedCornerShape(8.dp))
                    .clip(RoundedCornerShape(8.dp))
            )
        } else {
            Icon(
                imageVector = Lucide.Image,
                contentDescription = "Open Gallery",
                tint = Color.White,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}
