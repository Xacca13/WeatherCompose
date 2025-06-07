package com.example.weathercompose.controller

import com.example.weathercompose.dao.IWeather
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
        return controller.getForecast(key, q, days, aqi, alerts)
    }

    override suspend fun getCurrent(
        key: String,
        q: String,
        aqi: String
    ): Weather {
        return controller.getCurrent(key, q, aqi)
    }

}