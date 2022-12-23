package com.example.stockmarket.di

import android.app.Application
import androidx.room.Room
import com.example.stockmarket.data.local.StockMarketDao
import com.example.stockmarket.data.local.StockMarketDatabase
import com.example.stockmarket.data.remote.StockMarketApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockMarketApi(): StockMarketApi {
        return Retrofit.Builder()
            .baseUrl(StockMarketApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(StockMarketApi::class.java)
    }

    @Provides
    @Singleton
    fun provideStockMarketDB(
        application: Application,
    ): StockMarketDatabase {
        return Room.databaseBuilder(
            application,
            StockMarketDatabase::class.java,
            StockMarketDatabase.DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideStockMarketDao(db: StockMarketDatabase): StockMarketDao {
        return db.dao()
    }

}