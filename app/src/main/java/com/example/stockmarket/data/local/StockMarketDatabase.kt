package com.example.stockmarket.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyEntity::class],
    version = 1,
)
abstract class StockMarketDatabase : RoomDatabase() {

    abstract fun dao(): StockMarketDao

}