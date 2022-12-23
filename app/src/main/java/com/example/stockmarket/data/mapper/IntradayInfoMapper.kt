package com.example.stockmarket.data.mapper

import com.example.stockmarket.data.remote.dto.IntradayInfoDto
import com.example.stockmarket.domain.models.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*


fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"
    val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault())
    val localDateTime = LocalDateTime.parse(timeStamp, formatter)
    return IntradayInfo(localDateTime, close)
}