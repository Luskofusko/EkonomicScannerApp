package com.example.domain.repository

import com.example.domain.model.Receipt
import kotlinx.coroutines.flow.Flow

interface ReceiptRepository {
    suspend fun insertReceipt(receipt: Receipt)

    fun getReceipts(): Flow<List<Receipt>>
}
