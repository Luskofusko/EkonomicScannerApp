package com.example.presentation.composable

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.presentation.viewmodel.ReceiptViewModel

@Composable
fun MainComposable(
    navController: NavHostController,
    onCapturePhoto: () -> Unit,
    viewModel: ReceiptViewModel = hiltViewModel()
) {
    val receipts by viewModel.receipts.collectAsState()

    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Row {
                    FloatingActionButton(
                        onClick = onCapturePhoto,
                        modifier = Modifier.padding(end = 16.dp)
                    ) {
                        Text("+")
                    }

                    FloatingActionButton(
                        onClick = { navController.navigate("receiptList") }
                    ) {
                        Text("Add")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(receipts) { receipt ->
                ReceiptItem(
                    receipt,
                    onDelete = { viewModel.removeReceipt(it) },
                    onEdit = {
                        viewModel.setEditableReceipt(it)
                        Log.d("EditableReceipt", "Updated receipt: ${it.date}, ${it.totalAmount}")
                        navController.navigate("editReceiptScreen/${receipt.id}")

                    }
                )
            }
        }
    }
}
