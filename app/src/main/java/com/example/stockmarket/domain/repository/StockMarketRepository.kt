package com.example.stockmarket.domain.repository

import com.example.stockmarket.domain.models.Company
import com.example.stockmarket.domain.models.CompanyInfo
import com.example.stockmarket.domain.models.IntradayInfo
import com.example.stockmarket.utils.Resource
import kotlinx.coroutines.flow.Flow

interface StockMarketRepository {

    suspend fun getCompaniesList(
        getFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<Company>>>


    suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo>


    suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>>

}