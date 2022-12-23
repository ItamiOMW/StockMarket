package com.example.stockmarket.utils

sealed class Resource<T>(val data: T? = null, val message: String? = null) {

    class Success<T>(data: T?) : Resource<T>(data)

    class Error<T>(message: String?, data: T? = null) : Resource<T>(data = data, message = message)

    class Loading<T>(isLoading: Boolean = true) : Resource<T>()

}