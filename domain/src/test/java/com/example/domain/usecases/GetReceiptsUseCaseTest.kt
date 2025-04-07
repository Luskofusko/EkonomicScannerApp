package com.example.domain.usecases

import com.example.domain.model.Receipt
import com.example.domain.repository.ReceiptRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetReceiptsUseCaseTest {

    private lateinit var repository: ReceiptRepository
    private lateinit var useCase: GetReceiptsUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetReceiptsUseCase(repository)
    }

    @Test
    fun `invoke should return list of receipts from repository`() = runTest {
        // Given
        val expectedReceipts = listOf(
            Receipt(id = 1, photoPath = "path/receipt1.jpg", date = "2024-01-01", totalAmount = 50.0, currency = "EUR"),
            Receipt(id = 2, photoPath = "path/receipt2.jpg", date = "2024-01-02", totalAmount = 30.0, currency = "USD")
        )

        // When
        coEvery { repository.getReceipts() } returns flowOf(expectedReceipts)

        val result = useCase().toList()

        // Then
        assertEquals(expectedReceipts, result.first())
    }
}
