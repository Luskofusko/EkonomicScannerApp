package com.example.domain.usecases

import com.example.domain.model.Receipt
import com.example.domain.repository.ReceiptRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify

class DeleteReceiptUseCaseTest {

    private lateinit var repository: ReceiptRepository
    private lateinit var useCase: DeleteReceiptUseCase

    @Before
    fun setup() {
        repository = mock(ReceiptRepository::class.java)
        useCase = DeleteReceiptUseCase(repository)
    }

    @Test
    fun `invoke should call repository to delete receipt`() = runTest {
        // Given
        val receipt = Receipt(
            id = 1,
            photoPath = "path/to/photo.jpg",
            date = "2024-04-01",
            totalAmount = 25.0,
            currency = "EUR"
        )

        // When
        useCase(receipt)

        // Then
        verify(repository, times(1)).deleteReceipt(receipt)
    }
}
