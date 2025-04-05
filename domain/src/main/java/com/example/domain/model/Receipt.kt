package com.example.domain.model

data class Receipt(
    val id: Int = 0,
    val photoPath: String,
    val date: String,
    val totalAmount: Double,
    val currency: String
) {
    companion object {
        const val DEFAULT_TOTAL_AMOUNT = 10.0
        const val DEFAULT_CURRENCY = "EUR"
    }
}
