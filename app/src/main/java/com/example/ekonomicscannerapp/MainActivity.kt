package com.example.ekonomicscannerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EkonomicScannerAppTheme {
                val navController = rememberNavController()

                EkonomicNavHost(navHostController = navController)

            }
        }
    }
}

@Composable
fun EkonomicNavHost(navHostController: NavHostController) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(
            navController = navHostController,
            startDestination = "main",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("main") {
                MainComposable(navController = navHostController)
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
