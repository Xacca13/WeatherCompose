package com.example.weathercompose.model.weather

data class Weather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)