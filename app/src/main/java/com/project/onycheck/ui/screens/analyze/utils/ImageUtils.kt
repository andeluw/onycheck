package com.project.onycheck.ui.screens.analyze.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun getNewImageUri(context: Context): Uri {
    val directory = File(context.cacheDir, "images")
    directory.mkdirs()
    val file = File.createTempFile(
        "selected_image_",
        "${System.currentTimeMillis()}.jpg",
        directory
    )
    val authority = context.packageName + ".provider"
    return FileProvider.getUriForFile(
        context,
        authority,
        file,
    )
}

fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri {
    val uri = getNewImageUri(context)
    context.contentResolver.openOutputStream(uri)?.use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 95, it)
    }
    return uri
}

//fun saveBitmapToTempFile(context: Context, bitmap: Bitmap): Uri {
//    val file = File.createTempFile("cropped_image_${System.currentTimeMillis()}", ".png", context.cacheDir)
//    val fos = FileOutputStream(file)
//    bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
//    fos.flush()
//    fos.close()
//    return FileProvider.getUriForFile(context, "${context.packageName}.provider", file)
//}

internal fun saveBitmapToTempFile(context: Context, bitmap: Bitmap): Uri {
    val file = File(context.cacheDir, "cropped_image_${System.currentTimeMillis()}.jpg")
    file.outputStream().use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
    return Uri.fromFile(file)
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