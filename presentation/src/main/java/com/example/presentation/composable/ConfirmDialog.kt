package com.example.presentation.composable

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.presentation.R

@Composable
fun ConfirmDialog(
    title: String,
    message: String,
    confirmButtonText: String = stringResource(id = R.string.confirm_button),
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    showDialog: Boolean,
    confirmButtonColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.primary
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(title) },
            text = { Text(message) },
            confirmButton = {
                TextButton(onClick = {
                    onConfirm()
                    onDismiss()
                }) {
                    Text(confirmButtonText, color = confirmButtonColor)
                }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) {
                    Text(stringResource(id = R.string.cancel_button))
                }
            }
        )
    }
}
