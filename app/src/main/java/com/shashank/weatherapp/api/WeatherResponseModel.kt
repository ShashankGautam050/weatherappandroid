package com.shashank.weatherapp.api

data class WeatherResponseModel(
    val current: Current,
    val location: Location
)