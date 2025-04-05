package com.example.presentation.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.domain.model.Receipt
import com.example.presentation.R
import com.example.presentation.viewmodel.ReceiptViewModel

@Composable
fun EditReceiptScreen(
    receipt: Receipt,
    onSave: (String, Double) -> Unit,
    onCancel: () -> Unit,
    viewModel: ReceiptViewModel = hiltViewModel()
) {
    var editedDate by remember { mutableStateOf(receipt.date) }
    var editedAmount by remember { mutableStateOf(receipt.totalAmount.toString()) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        OutlinedTextField(
            value = editedDate,
            onValueChange = { editedDate = it },
            label = { Text(stringResource(id = R.string.date_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = editedAmount,
            onValueChange = { editedAmount = it },
            label = { Text(stringResource(id = R.string.total_amount_label)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            singleLine = true
        )

        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = onCancel) {
                Text(stringResource(id = R.string.cancel_button))
            }

            Button(onClick = {
                val amount = editedAmount.toDoubleOrNull()
                if (amount != null) {
                    val updatedReceipt = receipt.copy(
                        date = editedDate,
                        totalAmount = amount
                    )

                    viewModel.updateReceipt(updatedReceipt)

                    onSave(editedDate, amount)
                }
            }) {
                Text(stringResource(id = R.string.save_button))
            }
        }
    }
}
