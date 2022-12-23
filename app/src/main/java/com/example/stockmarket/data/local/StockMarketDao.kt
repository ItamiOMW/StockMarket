package com.example.stockmarket.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface StockMarketDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addListOfCompanies(companies: List<CompanyEntity>)

    @Query("DELETE FROM companies_table")
    suspend fun clearCompanies()

    @Query("""
            SELECT *
            FROM companies_table
            WHERE LOWER(name) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbol
        """)
    suspend fun searchCompanies(query: String = ""): List<CompanyEntity>

}