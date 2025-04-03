package com.example.ekonomicscannerapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.presentation.composable.CameraScreen
import com.example.presentation.ui.theme.EkonomicScannerAppTheme
import com.example.presentation.composable.MainComposable
import com.example.presentation.composable.ReceiptListScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                navigateToCamera()
            } else {
                Toast.makeText(this, "Camera permission is required to capture receipts.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun checkCameraPermissionAndNavigate() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            navigateToCamera()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    private fun navigateToCamera() {
        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Toast.makeText(this, "No camera detected on this device.", Toast.LENGTH_SHORT).show()
            return
        }

        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            try {
                val cameraProvider = cameraProviderFuture.get()
                if (cameraProvider.availableCameraInfos.isEmpty()) {
                    Toast.makeText(this, "No available camera.", Toast.LENGTH_SHORT).show()
                    return@addListener
                }
                navController.navigate("cameraScreen")

            } catch (e: Exception) {
                Toast.makeText(this, "Camera initialization failed.", Toast.LENGTH_SHORT).show()
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            navController = rememberNavController()
            EkonomicScannerAppTheme {
                EkonomicNavHost(navController as NavHostController, onCaptureClick = { checkCameraPermissionAndNavigate() })

            }
        }
    }
}

@Composable
fun EkonomicNavHost(navHostController: NavHostController, onCaptureClick: () -> Unit) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                MainComposable(navController = navHostController, onCapturePhoto = onCaptureClick)
            }
            composable("receiptList") {
                ReceiptListScreen(navController = navHostController)
            }
            composable("cameraScreen") {
                CameraScreen(
                    onPhotoCaptured = { uri ->
                        navHostController.popBackStack()
                    },
                    onBack = { navHostController.popBackStack() }
                )
            }
        }
    }
}
