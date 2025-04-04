package com.example.data.repository

import com.example.data.dao.ReceiptDao
import com.example.data.mappers.toDomainModel
import com.example.data.mappers.toEntity
import com.example.domain.model.Receipt
import com.example.domain.repository.ReceiptRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReceiptRepositoryImpl@Inject constructor(
    private val receiptDao: ReceiptDao
) : ReceiptRepository {

    override suspend fun insertReceipt(receipt: Receipt) {
        receiptDao.insert(receipt.toEntity())
    }

    override fun getReceipts(): Flow<List<Receipt>> {
        return receiptDao.getAllReceipts().map { list -> list.map { it.toDomainModel() } }
    }

    override suspend fun deleteReceipt(receipt: Receipt) {
        receiptDao.deleteReceipt(receipt.toEntity())
    }

    override suspend fun updateReceipt(updatedReceipt: Receipt) {
        receiptDao.updateReceipt(updatedReceipt.toEntity())
    }
}
