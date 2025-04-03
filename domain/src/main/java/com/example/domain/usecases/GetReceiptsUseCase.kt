package com.example.domain.usecases

import com.example.domain.model.Receipt
import com.example.domain.repository.ReceiptRepository
import kotlinx.coroutines.flow.Flow

class GetReceiptsUseCase(private val repository: ReceiptRepository) {
    operator fun invoke(): Flow<List<Receipt>> {
        return repository.getReceipts()
    }
}
