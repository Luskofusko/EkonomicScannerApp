package com.example.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.presentation.composable.CameraScreen
import com.example.presentation.composable.MainComposable
import com.example.presentation.composable.ReceiptListScreen

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
