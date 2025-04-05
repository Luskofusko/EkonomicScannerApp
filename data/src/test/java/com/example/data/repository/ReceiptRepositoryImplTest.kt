package com.example.data.repository

import com.example.data.dao.ReceiptDao
import com.example.data.mappers.toEntity
import com.example.data.model.ReceiptEntity
import com.example.domain.model.Receipt
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import org.junit.Assert.assertEquals

class ReceiptRepositoryImplTest {

    @Mock
    private lateinit var receiptDao: ReceiptDao

    private lateinit var receiptRepository: ReceiptRepositoryImpl

    @Before
    fun setup() {
        // Initialize mocks
        openMocks(this)
        receiptRepository = ReceiptRepositoryImpl(receiptDao)
    }

    @Test
    fun `insertReceipt should insert receipt into the dao`() = runTest {
        // Given
        val receipt = Receipt(
            id = 0,
            photoPath = "path/to/photo",
            date = "2025-04-06",
            totalAmount = 20.0,
            currency = "USD"
        )

        // When
        receiptRepository.insertReceipt(receipt)

        // Then
        verify(receiptDao).insert(receipt.toEntity())
    }

    @Test
    fun `getReceipts should return the list of receipts from dao`() = runTest {
        // Given
        val receiptEntity = ReceiptEntity(
            id = 1,
            photoPath = "path/to/photo",
            date = "2025-04-06",
            totalAmount = 20.0,
            currency = "USD"
        )
        val receiptList = listOf(receiptEntity)

        // When
        `when`(receiptDao.getAllReceipts()).thenReturn(flowOf(receiptList))

        val result = receiptRepository.getReceipts()

        // Then
        result.collect {
            assertEquals(1, it.size) // We expect one receipt in the list
            assertEquals("path/to/photo", it[0].photoPath)
        }
        verify(receiptDao).getAllReceipts()
    }

    @Test
    fun `deleteReceipt should delete a receipt from the dao`() = runTest {
        //Given
        val receipt = Receipt(
            id = 1,
            photoPath = "path/to/photo",
            date = "2025-04-06",
            totalAmount = 20.0,
            currency = "USD"
        )

        // When
        receiptRepository.deleteReceipt(receipt)

        // Then
        verify(receiptDao).deleteReceipt(receipt.toEntity())
    }

    @Test
    fun `updateReceipt should update a receipt in the dao`() = runTest {
        // Given
        val updatedReceipt = Receipt(
            id = 1,
            photoPath = "path/to/updated/photo",
            date = "2025-04-06",
            totalAmount = 30.0,
            currency = "USD"
        )

        // When
        receiptRepository.updateReceipt(updatedReceipt)

        // Then
        verify(receiptDao).updateReceipt(updatedReceipt.toEntity())
    }

}
