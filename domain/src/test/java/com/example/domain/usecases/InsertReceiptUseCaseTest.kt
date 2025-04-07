package com.example.domain.usecases

import com.example.domain.model.Receipt
import com.example.domain.repository.ReceiptRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class InsertReceiptUseCaseTest {

    private lateinit var useCase: InsertReceiptUseCase
    private lateinit var repository: ReceiptRepository

    @Before
    fun setup() {
        repository = mock(ReceiptRepository::class.java)
        useCase = InsertReceiptUseCase(repository)
    }

    @Test
    fun `invoke should call insertReceipt on repository`() = runTest {
        // Given
        val receipt = Receipt(
            id = 1,
            photoPath = "path/to/photo.jpg",
            date = "2025-04-05",
            totalAmount = 99.90,
            currency = "USD"
        )

        // When
        useCase.invoke(receipt)

        // Then
        verify(repository, times(1)).insertReceipt(receipt)
    }
}
