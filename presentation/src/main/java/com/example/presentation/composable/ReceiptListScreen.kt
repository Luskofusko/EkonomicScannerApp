package com.example.presentation.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.domain.model.Receipt
import com.example.presentation.viewmodel.ReceiptViewModel

@Composable
fun ReceiptListScreen(viewModel: ReceiptViewModel = hiltViewModel(), navController: NavHostController) {
    val receipts by viewModel.receipts.collectAsState()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        ReceiptInputSection { receipt ->
            viewModel.addReceipt(receipt)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(receipts) { receipt ->
                ReceiptItem(receipt)
            }
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("Go Back")
        }
    }
}

@Composable
fun ReceiptInputSection(onAddReceipt: (Receipt) -> Unit) {
    var photoPath by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var totalAmount by remember { mutableStateOf("") }
    var currency by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = photoPath,
            onValueChange = { photoPath = it },
            label = { Text("Photo Path") },
            modifier = Modifier.fillMaxWidth()
        )

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
                    photoPath = photoPath,
                    date = date,
                    totalAmount = totalAmount.toDoubleOrNull() ?: 0.0,
                    currency = currency
                )
                onAddReceipt(receipt)

                // Clear input fields after adding
                photoPath = ""
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

@Composable
fun ReceiptItem(receipt: Receipt) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Date: ${receipt.date}", style = MaterialTheme.typography.titleMedium)
            Text(text = "Total: ${receipt.totalAmount} ${receipt.currency}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}
