package com.example.stockmarket.domain.models

data class CompanyInfo(
    val name: String,
    val symbol: String,
    val description: String,
    val country: String,
    val industry: String,
)