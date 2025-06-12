package com.example.weathercompose.model.actvity

data class WeatherUI(
    val city: String,
    val time: String,
    val condition: String,
    val currentTemp: String,
    val minTemp: String,
    val maxTemp: String,
    val iconLink: String,
    val hours: List<WeatherUI>
)
