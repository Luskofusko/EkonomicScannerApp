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
import androidx.compose.material.icons.filled.InsertPhoto
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Receipt
import com.example.presentation.R
import com.example.presentation.ui.theme.Indigo80

@Composable
fun ReceiptItem(
    receipt: Receipt,
    onDelete: (Receipt) -> Unit,
    onEdit: (Receipt) -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showEditDialog by remember { mutableStateOf(false) }

    val isPlaceholder = receipt.photoPath.isBlank()
    val imagePainter = if (isPlaceholder) {
        rememberVectorPainter(image = Icons.Default.InsertPhoto)
    } else {
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
                modifier = Modifier.size(80.dp),
                colorFilter =  if (isPlaceholder) ColorFilter.tint(Indigo80) else null
            )
            Column(modifier = Modifier
                .weight(1f)
                .padding(16.dp), horizontalAlignment = Alignment.Start) {
                val dateText = stringResource(id = R.string.receipt_date, receipt.date)
                val totalText = stringResource(
                    id = R.string.receipt_total,
                    receipt.totalAmount,
                    receipt.currency
                )

                Text(text = dateText, style = MaterialTheme.typography.titleMedium)
                Text(text = totalText, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(
                onClick = { showEditDialog = true },
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
        title = stringResource(id = R.string.dialog_title_delete),
        message = stringResource(id = R.string.dialog_message_delete),
        confirmButtonText = stringResource(id = R.string.dialog_confirm_button_delete),
        confirmButtonColor = MaterialTheme.colorScheme.error,
        showDialog = showDeleteDialog,
        onConfirm = { onDelete(receipt) },
        onDismiss = { showDeleteDialog = false }
    )

    ConfirmDialog(
        title = stringResource(id = R.string.dialog_title_edit),
        message = stringResource(id = R.string.dialog_message_edit),
        confirmButtonText = stringResource(id = R.string.dialog_confirm_button_edit),
        showDialog = showEditDialog,
        onConfirm = { onEdit(receipt) },
        onDismiss = { showEditDialog = false }
    )
}
