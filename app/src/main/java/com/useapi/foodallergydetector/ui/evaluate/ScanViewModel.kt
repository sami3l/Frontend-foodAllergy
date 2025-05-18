package com.useapi.foodallergydetector.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.useapi.foodallergydetector.data.model.ScanRequest
import com.useapi.foodallergydetector.data.model.ScanResponse
import com.useapi.foodallergydetector.data.network.ScanApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ScanState {
    object Idle : ScanState()
    object Loading : ScanState()
    data class Success(val result: ScanResponse) : ScanState()
    data class Error(val message: String) : ScanState()
}

class ScanViewModel(
    private val api: ScanApi
) : ViewModel() {

    private val _state = MutableStateFlow<ScanState>(ScanState.Idle)
    val state: StateFlow<ScanState> = _state

    fun evaluate( productText: String,barcode: String, productName: String) {
        viewModelScope.launch {
            _state.value = ScanState.Loading
            try {
                val req = ScanRequest(
                    productText = "",
                    barcode = barcode,
                    productName = ""
                )
                val resp: ScanResponse = api.evaluate(req)
                _state.value = ScanState.Success(resp)
            } catch (t: Throwable) {
                _state.value = ScanState.Error(t.message ?: "Unknown error")
            }
        }
    }

}

class ScanViewModelFactory(private val api: ScanApi) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanViewModel::class.java)) {
            return ScanViewModel(api) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
