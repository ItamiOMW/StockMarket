package com.example.stockmarket.data.csv

import com.example.stockmarket.data.mapper.toIntradayInfo
import com.example.stockmarket.data.remote.dto.IntradayInfoDto
import com.example.stockmarket.domain.models.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader

class IntradayInfoParser: CSVParser<IntradayInfo> {

    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1) //Drop 1 field because it's a definition field
                .mapNotNull { line ->
                    val timeStamp = line.getOrNull(0) ?: return@mapNotNull null //Line under index 0 is timestamp
                    val close = line.getOrNull(4) ?: return@mapNotNull null //lINE under index 4 is close
                    val dto = IntradayInfoDto(timeStamp = timeStamp, close = close.toDouble())
                    dto.toIntradayInfo()
                }
        }
    }

}