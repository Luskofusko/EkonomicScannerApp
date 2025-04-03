package com.example.presentation.composable

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.common.util.concurrent.ListenableFuture
import java.io.File
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Composable
fun CameraScreen(
    onPhotoCaptured: (Uri) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalContext.current as LifecycleOwner
    val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> =
        remember { ProcessCameraProvider.getInstance(context) }
    val executor: ExecutorService = remember { Executors.newSingleThreadExecutor() }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        AndroidView(factory = { ctx ->
            val cameraProvider = cameraProviderFuture.get()
            val preview = androidx.camera.core.Preview.Builder().build()
            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = androidx.camera.core.CameraSelector.DEFAULT_BACK_CAMERA
            val previewView = androidx.camera.view.PreviewView(ctx)

            cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageCapture
            )
            preview.setSurfaceProvider(previewView.surfaceProvider)
            previewView
        }, modifier = Modifier.weight(1f))

        // Capture Button
        Button(
            onClick = {
                imageCapture?.let { capture ->
                    val file = File(context.externalCacheDir, "${System.currentTimeMillis()}.jpg")
                    val outputFileOptions = ImageCapture.OutputFileOptions.Builder(file).build()

                    capture.takePicture(
                        outputFileOptions,
                        executor,
                        object : ImageCapture.OnImageSavedCallback {
                            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                val savedUri = Uri.fromFile(file)
                                onPhotoCaptured(savedUri)
                            }

                            override fun onError(exception: ImageCaptureException) {
                                Log.e("CameraX", "Photo capture failed: ${exception.message}", exception)
                                Toast.makeText(context, "Capture failed", Toast.LENGTH_SHORT).show()
                            }
                        }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Capture Photo")
        }

        Button(
            onClick = {
                onBack()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text("Go Back")
        }
    }
}
