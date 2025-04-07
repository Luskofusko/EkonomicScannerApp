package com.example.domain.usecases

import com.example.domain.model.Receipt
import com.example.domain.repository.ReceiptRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class EditReceiptUseCaseTest {

    private lateinit var repository: ReceiptRepository
    private lateinit var useCase: EditReceiptUseCase

    @Before
    fun setup() {
        repository = mock(ReceiptRepository::class.java)
        useCase = EditReceiptUseCase(repository)
    }

    @Test
    fun `invoke should call repository to update receipt`() = runTest {
        // Given
        val updatedReceipt = Receipt(
            id = 2,
            photoPath = "path/to/photo.jpg",
            date = "2025-04-05",
            totalAmount = 50.0,
            currency = "EUR"
        )

        // When
        useCase(updatedReceipt)

        // Then
        verify(repository, times(1)).updateReceipt(updatedReceipt)
    }
}
