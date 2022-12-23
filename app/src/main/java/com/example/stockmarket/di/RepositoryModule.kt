package com.example.stockmarket.di

import com.example.stockmarket.data.csv.CSVParser
import com.example.stockmarket.data.csv.CompanyParser
import com.example.stockmarket.data.csv.IntradayInfoParser
import com.example.stockmarket.data.repository.StockMarketRepositoryImpl
import com.example.stockmarket.domain.models.Company
import com.example.stockmarket.domain.models.IntradayInfo
import com.example.stockmarket.domain.repository.StockMarketRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {


    @Binds
    @Singleton
    fun bindCompanyParser(
        companyParser: CompanyParser,
    ): CSVParser<Company>

    @Binds
    @Singleton
    fun bindIntradayParser(
        intradayInfoParser: IntradayInfoParser,
    ): CSVParser<IntradayInfo>

    @Binds
    @Singleton
    fun bindStockMarketRepository(
        stockMarketRepositoryImpl: StockMarketRepositoryImpl,
    ): StockMarketRepository

}