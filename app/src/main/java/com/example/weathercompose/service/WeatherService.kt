package com.example.weathercompose.service

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weathercompose.controller.WeatherController
import com.example.weathercompose.model.actvity.WeatherUI
import com.example.weathercompose.model.weather.Hour
import com.example.weathercompose.model.weather.Weather
import kotlinx.coroutines.launch

class WeatherService : ViewModel() {
    private val TOKEN = "3204d89db56b4704a5f91432252303"

    private val controller: WeatherController = WeatherController()

    fun getForecastWeather(
        q: String,
        daysList: MutableState<List<WeatherUI>>,
        currentDay: MutableState<WeatherUI>
    ) {
        viewModelScope.launch {
            try {
                parsingWeather(controller.getForecast(TOKEN, q, "3"), daysList, currentDay)
            } catch(error: Exception) {
                error.printStackTrace()
            }
        }

    }
    fun getCurrentWeather(
        q: String,
        daysList: MutableState<List<WeatherUI>>,
        currentDay: MutableState<WeatherUI>
    ) {
        viewModelScope.launch {
            try {
                parsingWeather(controller.getCurrent(TOKEN, q), daysList, currentDay)
            } catch(error: Exception) {
                error.printStackTrace()
            }
        }
    }




    private fun parsingWeather(weatherRetrofit: Weather, daysList: MutableState<List<WeatherUI>>, currentDay: MutableState<WeatherUI>) {
        currentDay.value = parsingCurrentDay(weatherRetrofit)
        daysList.value = parsingDays(weatherRetrofit)
    }

    private fun parsingCurrentDay(weatherRetrofit: Weather): WeatherUI {
        return WeatherUI(
            weatherRetrofit.location.name,
            weatherRetrofit.location.localtime,
            weatherRetrofit.current.condition.text,
            "${weatherRetrofit.current.temp_c.toInt()}°",
            "${weatherRetrofit.forecast.forecastday[0].day.mintemp_c.toInt()}°",
            "${weatherRetrofit.forecast.forecastday[0].day.maxtemp_c.toInt()}°",
            "https:${weatherRetrofit.current.condition.icon}",
            parsingHours(weatherRetrofit.forecast.forecastday[0].hour)
        )
    }

    private fun parsingDays(weather: Weather): List<WeatherUI> {
        val list = ArrayList<WeatherUI>()
        list.add(parsingCurrentDay(weather))
        val name = weather.location.name
        for (i in 1 until weather.forecast.forecastday.size) {
            val day = weather.forecast.forecastday[i]
            val item = WeatherUI(
                name,
                day.date,
                day.day.condition.text.trim(),
                "",
                "${day.day.mintemp_c.toInt()}°",
                "${day.day.maxtemp_c.toInt()}°",
                "https:${day.day.condition.icon}",
                parsingHours(day.hour)
            )
            list.add(item)
        }
        return list
    }

    private fun parsingHours(hours: List<Hour>): List<WeatherUI> {
        val list = ArrayList<WeatherUI>()
        for (i in 0 until hours.size) {
            val item = WeatherUI(
                "",
                hours[i].time.substring(10),
                hours[i].condition.text.trim(),
                hours[i].temp_c.toInt().toString() + "°",
                "",
                "",
                "https:" + hours[i].condition.icon,
                listOf()
            )
            list.add(item)
        }
        return list
    }
}