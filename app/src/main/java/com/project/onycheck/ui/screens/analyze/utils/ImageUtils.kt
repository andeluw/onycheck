package com.project.onycheck.ui.screens.analyze.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

fun getNewImageUri(context: Context, isCropped: Boolean = false): Uri {
    val directory = File(context.cacheDir, "images")
    directory.mkdirs()

    val prefix = if (isCropped) "cropped_" else ""
    val file = File.createTempFile(
        "${prefix}nail_image_${System.currentTimeMillis()}",
        ".png",
        directory
    )

    val authority = "${context.packageName}.provider"
    return FileProvider.getUriForFile(context, authority, file)
}

//fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri {
//    val uri = getNewImageUri(context)
//    context.contentResolver.openOutputStream(uri)?.use {
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)
//    }
//    return uri
//}

//fun saveBitmapToTempFile(context: Context, bitmap: Bitmap): Uri {
//    val file = File.createTempFile("cropped_image_${System.currentTimeMillis()}", ".png", context.cacheDir)
//    val fos = FileOutputStream(file)
//    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//    fos.flush()
//    fos.close()
//    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
//}

internal fun saveBitmapToTempFile(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "cropped_image_${System.currentTimeMillis()}.png")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, it)
    }
    return Uri.fromFile(file)
//    return getNewImageUri(context, isCropped = true)
}

fun convertBitmapToBase64(bitmap: Bitmap): String {
    val outputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

    val byteArray = outputStream.toByteArray()
    return Base64.encodeToString(byteArray, Base64.DEFAULT)
}


internal fun takePhoto(
    context: Context,
    imageCapture: ImageCapture,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    val photoFile = File(context.cacheDir, "camera_photo_${System.currentTimeMillis()}.png")
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                val savedUri = outputFileResults.savedUri ?: Uri.fromFile(photoFile)
//                val savedUri = outputFileResults.savedUri ?: FileProvider.getUriForFile(context, "${context.packageName}.provider", photoFile)
                onImageCaptured(savedUri)
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e("CameraView", "Take photo error", exception)
                onError(exception)
            }
        }
    )
}

//fun takePhoto(
//    context: Context,
//    imageCapture: ImageCapture,
//    onImageCaptured: (Uri) -> Unit,
//    onError: (ImageCaptureException) -> Unit
//) {
//    // 1. Get a secure content:// URI from our helper function first.
//    // This is where the photo will be saved.
//    val photoUri = getNewImageUri(context)
//
//    // 2. Create OutputFileOptions using the ContentResolver and the URI.
//    // This is the modern, secure way to specify the output location.
//    val outputOptions = ImageCapture.OutputFileOptions.Builder(
//        context.contentResolver,
//        photoUri,
//        ContentValues() // We can pass empty ContentValues for a simple save
//    ).build()
//
//    imageCapture.takePicture(
//        outputOptions,
//        ContextCompat.getMainExecutor(context),
//        object : ImageCapture.OnImageSavedCallback {
//            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
//                // The output URI will be the same one we provided.
//                // We use our 'photoUri' variable to ensure it's the correct one.
//                onImageCaptured(photoUri)
//            }
//
//            override fun onError(exception: ImageCaptureException) {
//                Log.e("CameraView", "Take photo error", exception)
//                onError(exception)
//            }
//        }
//    )
//}

