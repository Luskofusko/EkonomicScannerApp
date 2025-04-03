package com.example.presentation.composable

import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
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
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }

    // Get camera provider and store it
    LaunchedEffect(Unit) {
        cameraProvider = cameraProviderFuture.get()
    }

    // Clean up CameraX resources when leaving screen
    DisposableEffect(Unit) {
        val provider = cameraProviderFuture.get()
        cameraProvider = provider
        onDispose {
            provider.unbindAll()  // Ensure we release the camera when leaving the screen
            executor.shutdown()
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        AndroidView(factory = { ctx ->
            val previewView = PreviewView(ctx)

            cameraProviderFuture.get().also { provider ->
                cameraProvider = provider // Store reference to camera provider
                val preview = Preview.Builder().build()
                imageCapture = ImageCapture.Builder().build()
                val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

                provider.bindToLifecycle(lifecycleOwner, cameraSelector, preview, imageCapture)
                preview.setSurfaceProvider(previewView.surfaceProvider)
            }

            previewView
        }, modifier = Modifier.weight(1f))

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
                cameraProvider?.unbindAll() // Unbind camera before navigating back
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
