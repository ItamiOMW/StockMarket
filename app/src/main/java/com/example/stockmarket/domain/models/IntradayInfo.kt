package com.example.stockmarket.domain.models

import java.time.LocalDateTime

data class IntradayInfo(
    private val date: LocalDateTime,
    private val close: Double,
)