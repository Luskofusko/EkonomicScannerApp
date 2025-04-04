package com.example.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Receipt

@Composable
fun ReceiptItem(receipt: Receipt) {
    var showDialog by remember { mutableStateOf(false) }
    val imagePainter = if (receipt.photoPath.toIntOrNull() != null) {
        // If photoPath is a resource ID
        painterResource(id = receipt.photoPath.toInt())
    } else {
        // If photoPath is a URI or file path
        rememberAsyncImagePainter(model = receipt.photoPath)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable { /* Open receipt details */ },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Receipt Image",
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text(text = "Date: ${receipt.date}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Total: ${receipt.totalAmount} ${receipt.currency}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(
                onClick = { showDialog = true },
                modifier = Modifier.padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Receipt",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Delete Receipt?") },
            text = { Text("Are you sure you want to delete this receipt? This action cannot be undone.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        //onDelete()
                        showDialog = false
                    }
                ) {
                    Text("Delete", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
