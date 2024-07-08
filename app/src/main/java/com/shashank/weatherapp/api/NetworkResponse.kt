package com.shashank.weatherapp.api

sealed class NetworkResponse<out T> {
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class Failure(val message: String) : NetworkResponse<Nothing>()
    object Loading : NetworkResponse<Nothing>()


}