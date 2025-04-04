package com.example.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Receipt
import com.example.presentation.R
import com.example.presentation.viewmodel.ReceiptViewModel

@Composable
fun ReceiptListScreen(viewModel: ReceiptViewModel = hiltViewModel(), navController: NavHostController) {

    LazyColumn(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        item {
            ReceiptInputSection { receipt ->
                viewModel.addReceipt(receipt)
            }
        }
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        item {
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}

@Composable
fun ReceiptInputSection(onAddReceipt: (Receipt) -> Unit) {
    var date by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {

        val placeholderImage = R.drawable.placeholder_receipt_foreground

        OutlinedTextField(
            value = date,
            onValueChange = { date = it },
            label = { Text("Date (YYYY-MM-DD)") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = totalAmount,
            onValueChange = { totalAmount = it },
            label = { Text("Total Amount") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = currency,
            onValueChange = { currency = it },
            label = { Text("Currency") },
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = {
                val receipt = Receipt(
                    photoPath = placeholderImage.toString(),
                    date = date,
                    totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,
                    currency = currency
                )
                onAddReceipt(receipt)

                // Clear input fields after adding
                date = ""
                totalAmount = ""
                currency = ""
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Add Receipt")
        }
    }
}
