package com.example.stockmarket.data.mapper

import com.example.stockmarket.data.remote.dto.CompanyInfoDto
import com.example.stockmarket.domain.models.CompanyInfo


fun CompanyInfoDto.toCompanyInfo() = CompanyInfo(
    name = name ?: "",
    symbol = symbol ?: "",
    description = description ?: "",
    country = country ?: "",
    industry = industry ?: ""
)