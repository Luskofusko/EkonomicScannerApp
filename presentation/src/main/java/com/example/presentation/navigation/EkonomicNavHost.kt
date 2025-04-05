package com.example.presentation.navigation

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.domain.model.Receipt
import com.example.presentation.composable.CameraScreen
import com.example.presentation.composable.EditReceiptScreen
import com.example.presentation.composable.MainComposable
import com.example.presentation.composable.ReceiptListScreen
import com.example.presentation.utils.DateUtils
import com.example.presentation.viewmodel.ReceiptViewModel

@Composable
fun EkonomicNavHost(navHostController: NavHostController, onCaptureClick: () -> Unit) {

    val receiptViewModel: ReceiptViewModel = hiltViewModel()

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
                        val currentDate = DateUtils.getCurrentDate()
                        Handler(Looper.getMainLooper()).post {
                            receiptViewModel.addReceipt(
                                Receipt(
                                    photoPath = uri.toString(),
                                    date = currentDate,
                                    totalAmount = Receipt.DEFAULT_TOTAL_AMOUNT,
                                    currency = Receipt.DEFAULT_CURRENCY)
                            )
                            navHostController.popBackStack()
                        }
                    },
                    onBack = { navHostController.popBackStack() }
                )
            }
            composable("editReceiptScreen/{receiptId}") { backStackEntry ->
                val receiptId = backStackEntry.arguments?.getString("receiptId")?.toIntOrNull()
                val receipt = receiptViewModel.receipts.collectAsState().value.find { it.id == receiptId }

                Log.d("NavHost", "Editable Receipt: $receipt")

                if (receipt != null) {
                    EditReceiptScreen(
                        receipt = receipt,
                        onSave = { newDate, newAmount ->
                            receiptViewModel.updateReceipt(
                                receipt.copy(date = newDate, totalAmount = newAmount)
                            )
                            navHostController.popBackStack()
                        },
                        onCancel = {
                            navHostController.popBackStack()
                        }
                    )
                }
            }
        }
    }
}
