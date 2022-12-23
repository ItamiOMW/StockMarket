package com.example.stockmarket.data.remote

import com.example.stockmarket.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockMarketApi {

    @GET(QUERY_COMPANY_LIST)
    suspend fun getCompanies(
        @Query(API_KEY_PARAM) apikey: String = API_KEY_VALUE,
    ): ResponseBody


    @GET(QUERY_INTRADAY_INFO)
    suspend fun getIntradayInfo(
        @Query(SYMBOL_PARAM) symbol: String,
        @Query(API_KEY_PARAM) apikey: String = API_KEY_VALUE,
    ): ResponseBody


    @GET(QUERY_COMPANY_INFO)
    suspend fun getCompanyInfo(
        @Query(SYMBOL_PARAM) symbol: String,
        @Query(API_KEY_PARAM) apikey: String = API_KEY_VALUE,
    ): CompanyInfoDto


    companion object {

        //BASE URL OF THE API
        const val BASE_URL = "https://www.alphavantage.co/"

        //MY FREE API KEY
        private const val API_KEY_VALUE = "I484NN6CI8GF31DB"

        //PARAM FOR API KEY
        private const val API_KEY_PARAM = "apikey"

        //PARAM FOR SYMBOL
        private const val SYMBOL_PARAM = "symbol"

        //QUERY FOR LIST OF COMPANIES
        private const val QUERY_COMPANY_LIST = "query?function=LISTING_STATUS"

        //QUERY FOR INTRADAY INFO
        private const val QUERY_INTRADAY_INFO = "query?function=TIME_SERIES_INTRADAY&interval=60min"

        //QUERY FOR COMPANY INFO
        private const val QUERY_COMPANY_INFO = "query?function=OVERVIEW"
    }

}