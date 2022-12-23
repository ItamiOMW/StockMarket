package com.example.stockmarket.presentation.company_info_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarket.domain.models.CompanyInfo
import com.example.stockmarket.domain.models.IntradayInfo
import com.example.stockmarket.domain.repository.StockMarketRepository
import com.example.stockmarket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyInfoViewModel @Inject constructor(
    private val repository: StockMarketRepository,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private var _state by mutableStateOf(CompanyInfoState())
    val state: CompanyInfoState
        get() = _state

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            _state = state.copy(isLoading = true)
            val intradaysResponse = async { repository.getIntradayInfo(symbol) }
            val companyInfoResponse = async { repository.getCompanyInfo(symbol) }
            intradaysResponse.await().apply {
                collectIntradaysList(this)
            }
            companyInfoResponse.await().apply {
                collectCompanyInfo(this)
            }
        }
    }

    private fun collectCompanyInfo(companyInfoResponse: Resource<CompanyInfo>) {
        viewModelScope.launch {
            when (companyInfoResponse) {
                is Resource.Success -> {
                    _state = state.copy(
                        companyInfo = companyInfoResponse.data,
                        isLoading = false,
                        error = null
                    )
                }
                is Resource.Error -> {
                    _state = state.copy(isLoading = false, error = companyInfoResponse.message)
                }
                is Resource.Loading -> Unit //We handle Loading in the current View Model, but not in the Repository
            }
        }
    }

    private fun collectIntradaysList(intradayListResponse: Resource<List<IntradayInfo>>) {
        when (intradayListResponse) {
            is Resource.Success -> {
                _state = state.copy(
                    intradayList = intradayListResponse.data ?: emptyList(),
                    isLoading = false,
                    error = null
                )
            }
            is Resource.Error -> {
                _state = state.copy(isLoading = false, error = intradayListResponse.message)
            }
            is Resource.Loading -> Unit //We handle Loading in the current View Model, but not in the Repository
        }
    }

}