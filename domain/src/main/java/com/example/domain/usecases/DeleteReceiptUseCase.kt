package com.example.domain.usecases

import com.example.domain.model.Receipt
import com.example.domain.repository.ReceiptRepository

class DeleteReceiptUseCase(private val repository: ReceiptRepository) {
    suspend operator fun invoke(receipt: Receipt) {
        repository.deleteReceipt(receipt)
    }
}
