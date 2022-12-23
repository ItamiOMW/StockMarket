package com.example.stockmarket.presentation.companies_screen

sealed class CompaniesEvent {

    object Refresh: CompaniesEvent()

    data class OnSearchQueryChange(val query: String): CompaniesEvent()

}