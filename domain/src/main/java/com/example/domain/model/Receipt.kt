package com.example.domain.model

data class Receipt(
    val id: Int = 0,
    val photoPath: String,
    val date: String,
    val totalAmount: Double,
    val currency: String
)
