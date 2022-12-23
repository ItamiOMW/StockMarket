package com.example.stockmarket.data.mapper

import com.example.stockmarket.data.local.CompanyEntity
import com.example.stockmarket.domain.models.Company


fun CompanyEntity.toCompany() = Company(
    name = name,
    symbol = symbol,
    exchange = exchange
)

fun Company.toCompanyEntity() = CompanyEntity(
    name = name,
    symbol = symbol,
    exchange = exchange
)