package com.example.presentation.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.domain.model.Receipt

@Composable
fun ReceiptItem(receipt: Receipt) {

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
        }
    }
}
