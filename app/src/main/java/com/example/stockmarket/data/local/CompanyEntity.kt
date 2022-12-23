package com.example.stockmarket.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "companies_table")
data class CompanyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = UNKNOWN_ID,
    val name: String,
    val symbol: String,
    val exchange: String,
) {

    companion object {

        private const val UNKNOWN_ID = 0
    }
}