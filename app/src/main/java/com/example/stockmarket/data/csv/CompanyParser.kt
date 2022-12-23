package com.example.stockmarket.data.csv

import com.example.stockmarket.domain.models.Company
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader

class CompanyParser : CSVParser<Company> {

    override suspend fun parse(stream: InputStream): List<Company> {
        val csvReader = CSVReader(InputStreamReader(stream))
        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1) //Drop 1 field because it's a definition field
                .mapNotNull { line ->
                    val name = line.getOrNull(1) //Line under index 1 is the name
                    val symbol = line.getOrNull(0) //Line under index 0 is the symbol
                    val exchange = line.getOrNull(2) //Line under index 2 is the exchange
                    Company(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also { csvReader.close() }
        }
    }
}