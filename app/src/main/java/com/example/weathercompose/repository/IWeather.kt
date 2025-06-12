package com.example.weathercompose.repository

import com.example.weathercompose.model.weather.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeather {

    @GET("forecast.json")
    suspend fun getForecast(@Query("key") key: String,
                            @Query("q") q: String,
                            @Query("days") days: String,
                            @Query("aqi") aqi: String = "no",
                            @Query("alerts") alerts: String = "no"): Weather

    @GET("current.json")
    suspend fun getCurrent(@Query("key") key: String,
                            @Query("q") q: String,
                            @Query("aqi") aqi: String = "no"): Weather

}