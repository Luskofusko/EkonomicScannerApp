package com.example.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.Receipt
import com.example.domain.usecases.DeleteReceiptUseCase
import com.example.domain.usecases.GetReceiptsUseCase
import com.example.domain.usecases.InsertReceiptUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceiptViewModel @Inject constructor(
    private val insertReceiptUseCase: InsertReceiptUseCase,
    private val getReceiptsUseCase: GetReceiptsUseCase,
    private val deleteReceiptUseCase: DeleteReceiptUseCase
) : ViewModel() {

    private val _receipts = MutableStateFlow<List<Receipt>>(emptyList())
    val receipts: StateFlow<List<Receipt>> = _receipts.asStateFlow()

    private val _editableReceipt = MutableStateFlow<Receipt?>(null)
    val editableReceipt: StateFlow<Receipt?> = _editableReceipt.asStateFlow()

    fun setEditableReceipt(receipt: Receipt) {
        _editableReceipt.value = receipt
        Log.d("ViewModel", "Editable receipt set: ${_editableReceipt.value}")
    }

    fun clearEditableReceipt() {
        _editableReceipt.value = null
    }

    init {
        observeReceipts()
    }

    private fun observeReceipts() {
        viewModelScope.launch {
            getReceiptsUseCase().collectLatest { receiptList ->
                _receipts.value = receiptList
            }
        }
    }

    fun addReceipt(receipt: Receipt) {
        viewModelScope.launch {
            insertReceiptUseCase(receipt)
        }
    }

    fun removeReceipt(receipt: Receipt) {
        viewModelScope.launch {
            deleteReceiptUseCase(receipt)
        }
    }

    fun updateReceipt(string: String, double: Double) {
        viewModelScope.launch {
            //update use case
        }
    }
}
