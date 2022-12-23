package com.example.stockmarket.presentation.company_info_screen

import com.example.stockmarket.domain.models.CompanyInfo
import com.example.stockmarket.domain.models.IntradayInfo

data class CompanyInfoState(
    val intradayList: List<IntradayInfo> = emptyList(),
    val companyInfo: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
) {
}