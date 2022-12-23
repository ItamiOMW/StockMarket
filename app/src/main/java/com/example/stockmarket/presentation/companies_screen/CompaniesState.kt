package com.example.stockmarket.presentation.companies_screen

import com.example.stockmarket.domain.models.Company

data class CompaniesState(
    val companiesList: List<Company> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val searchQuery: String = "",
    val error: String? = null,
)