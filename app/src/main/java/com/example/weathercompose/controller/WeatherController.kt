package com.example.weathercompose.controller

import com.example.weathercompose.repository.IWeather
import com.example.weathercompose.model.weather.Weather

class WeatherController : IWeather {
    private var controller: IWeather = RetrofitController().create().create(IWeather::class.java)

    override suspend fun getForecast(
        key: String,
        q: String,
        days: String,
        aqi: String,
        alerts: String
    ): Weather {
        return controller.getForecast(key, q, days)
    }

    override suspend fun getCurrent(
        key: String,
        q: String,
        aqi: String
    ): Weather {
        return controller.getCurrent(key, q)
    }

}