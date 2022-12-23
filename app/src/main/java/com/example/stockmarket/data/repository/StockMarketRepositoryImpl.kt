package com.example.stockmarket.data.repository

import android.app.Application
import com.example.stockmarket.R
import com.example.stockmarket.data.csv.CSVParser
import com.example.stockmarket.data.local.StockMarketDao
import com.example.stockmarket.data.mapper.toCompany
import com.example.stockmarket.data.mapper.toCompanyEntity
import com.example.stockmarket.data.mapper.toCompanyInfo
import com.example.stockmarket.data.remote.StockMarketApi
import com.example.stockmarket.domain.models.Company
import com.example.stockmarket.domain.models.CompanyInfo
import com.example.stockmarket.domain.models.IntradayInfo
import com.example.stockmarket.domain.repository.StockMarketRepository
import com.example.stockmarket.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockMarketRepositoryImpl @Inject constructor (
    private val api: StockMarketApi,
    private val dao: StockMarketDao,
    private val application: Application,
    private val companyCSVParser: CSVParser<Company>,
    private val intradayCSVParser: CSVParser<IntradayInfo>,
) : StockMarketRepository {

    override suspend fun getCompaniesList(
        getFromRemote: Boolean,
        query: String,
    ): Flow<Resource<List<Company>>> {
        return flow {
            emit(Resource.Loading(true))
            val cachedCompanyList = dao.searchCompanies(query)
            emit(Resource.Success(
                cachedCompanyList.map { it.toCompany() })
            )

            val isDbEmpty = cachedCompanyList.isEmpty() && query.isEmpty()

            val shouldLoadFromCache = !isDbEmpty && !getFromRemote

            if (shouldLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            //Getting data from api
            val remoteCompaniesList = try {
                val response = api.getCompanies()
                companyCSVParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.failed_attempt_companies)))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(application.getString(R.string.failed_attempt_companies)))
                null
            }

            remoteCompaniesList?.let { list ->
                //Clear cache
                dao.clearCompanies()
                //Caching data
                dao.addListOfCompanies(list.map { it.toCompanyEntity() })
                //Send companies from cache
                emit(Resource.Success(dao.searchCompanies().map { it.toCompany() }))
                emit(Resource.Loading(false))
            }

        }
    }


    override suspend fun getCompanyInfo(symbol: String): Resource<CompanyInfo> {
        return try {
            val companyInfoDto = api.getCompanyInfo(symbol)
            Resource.Success(companyInfoDto.toCompanyInfo())
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(application.getString((R.string.failed_attempt_company_info)))
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(application.getString((R.string.failed_attempt_company_info)))
        }
    }


    override suspend fun getIntradayInfo(symbol: String): Resource<List<IntradayInfo>> {
        return try {
            val response = api.getIntradayInfo(symbol)
            val listIntraday = intradayCSVParser.parse(response.byteStream())
            Resource.Success(listIntraday)
        } catch (e: IOException) {
            e.printStackTrace()
            Resource.Error(application.getString(R.string.failed_attempt_intraday))
        } catch (e: HttpException) {
            e.printStackTrace()
            Resource.Error(application.getString(R.string.failed_attempt_intraday))
        }
    }

}