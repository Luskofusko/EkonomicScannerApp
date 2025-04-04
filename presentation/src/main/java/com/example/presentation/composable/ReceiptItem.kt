package com.example.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
fun ReceiptItem(
    receipt: Receipt,
    onDelete: (Receipt) -> Unit,
    onEdit: (Receipt) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

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
                .clickable { /* TODO Open receipt details */ },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = imagePainter,
                contentDescription = "Receipt Image",
                modifier = Modifier.size(80.dp)
            )
            Column(modifier = Modifier.weight(1f).padding(16.dp), horizontalAlignment = Alignment.Start) {
                Text(text = "Date: ${receipt.date}", style = MaterialTheme.typography.titleMedium)
                Text(text = "Total: ${receipt.totalAmount} ${receipt.currency}", style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(
                onClick = { showEditDialog = true /* TODO: trigger edit flow */ },
                modifier = Modifier.padding(end = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Receipt"
                )
            }
            IconButton(
                onClick = { showDeleteDialog = true },
                modifier = Modifier.padding(start = 2.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Receipt",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
    ConfirmDialog(
        title = "Delete Receipt?",
        message = "Are you sure you want to delete this receipt? This action cannot be undone.",
        confirmButtonText = "Delete",
        confirmButtonColor = MaterialTheme.colorScheme.error,
        showDialog = showDeleteDialog,
        onConfirm = { onDelete(receipt) },
        onDismiss = { showDeleteDialog = false }
    )

    ConfirmDialog(
        title = "Edit Receipt?",
        message = "Would you like to edit this receipt?",
        confirmButtonText = "Edit",
        showDialog = showEditDialog,
        onConfirm = { onEdit(receipt) },
        onDismiss = { showEditDialog = false }
    )
}
