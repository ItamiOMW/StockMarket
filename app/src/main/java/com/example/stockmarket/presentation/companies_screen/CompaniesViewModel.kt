package com.example.stockmarket.presentation.companies_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockmarket.domain.repository.StockMarketRepository
import com.example.stockmarket.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val repository: StockMarketRepository,
) : ViewModel() {

    private var _state by mutableStateOf(CompaniesState())
    val state: CompaniesState
        get() = _state

    init {
        getCompaniesList()
    }

    //This job helps with the delay before searching by query
    private var searchJob: Job? = null

    fun onEvent(event: CompaniesEvent) {
        when (event) {
            is CompaniesEvent.Refresh -> {
                getCompaniesList(getFromRemote = true)
            }
            is CompaniesEvent.OnSearchQueryChange -> {
                _state = state.copy(searchQuery = event.query)
                //After every OnSearchQueryChange cancel the job and run new one
                searchJob?.cancel()
                searchJob = viewModelScope.launch {
                    delay(500L)
                    //If nothing followed after 0.5 sec then get the companies list by query
                    getCompaniesList()
                }
            }
        }
    }

    private fun getCompaniesList(
        query: String = state.searchQuery.lowercase(),
        getFromRemote: Boolean = false,
    ) {
        viewModelScope.launch {
            repository.getCompaniesList(getFromRemote, query).collect { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { list -> _state = state.copy(companiesList = list) }
                    }
                    is Resource.Error -> {
                        _state = state.copy(error = response.message)
                    }
                    is Resource.Loading -> {
                        _state = state.copy(isLoading = response.isLoading)
                    }
                }
            }
        }
    }
}