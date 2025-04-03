package com.example.data.mappers

import com.example.data.model.ReceiptEntity
import com.example.domain.model.Receipt

fun ReceiptEntity.toDomainModel(): Receipt {
    return Receipt(
        id = this.id,
        photoPath = this.photoPath,
        date = this.date,
        totalAmount = this.totalAmount,
        currency = this.currency
    )
}

fun Receipt.toEntity(): ReceiptEntity {
    return ReceiptEntity(
        id = this.id,
        photoPath = this.photoPath,
        date = this.date,
        totalAmount = this.totalAmount,
        currency = this.currency
    )
}
