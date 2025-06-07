package com.example.weathercompose.service

import android.content.Context
import android.util.Log
import androidx.compose.runtime.MutableState
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weathercompose.model.actvity.Weather
import org.json.JSONArray
import org.json.JSONObject

class WeatherService {
    private val TOKEN = "3204d89db56b4704a5f91432252303"

//    private val controller: WeatherController = WeatherController()

//    suspend fun getForecastWeather(
//        q: String
//    ): Weather? {
//        var weather: Weather? = controller.getForecast(TOKEN, q, "3", "no", "no")
//        return weather
//    }
//    suspend fun getCurrentWeather(
//        q: String
//    ): Weather? {
//        var weather: Weather? = controller.getCurrent(TOKEN, q, "no")
//        return weather
//    }


    fun requestWeatherData(city: String, context: Context?, daysList: MutableState<List<Weather>>) {
        val link = "https://api.weatherapi.com/v1/forecast.json" +
                "?key=$TOKEN" +
                "&q=$city" +
                "&days=3" +
                "&aqi=no" +
                "&alerts=no"
        val req = StringRequest(
            Request.Method.GET,
            link,
            { result -> parseRequestWeather(result, daysList) },
            { err -> Log.e("MyLog", "Error: $err")}
        )
        val queue = Volley.newRequestQueue(context)
        queue.add(req)
    }


    private fun parseRequestWeather(result: String, daysList: MutableState<List<Weather>>) {
        val body =  JSONObject(result)
        daysList.value = parseDaysWeather(body)
        parseBodyToWeather(body, daysList.value[0])
    }


    private fun parseBodyToWeather(body: JSONObject, weatherItem: Weather): Weather {
        val item = Weather(
            body.getJSONObject("location").getString("name"),
            body.getJSONObject("current").getString("last_updated"),
            body.getJSONObject("current").getJSONObject("condition").getString("text").trim(),
            body.getJSONObject("current").getString("temp_c").toFloat().toInt().toString() + "°",
            weatherItem.minTemp,
            weatherItem.maxTemp,
            "https:" + body.getJSONObject("current").getJSONObject("condition").getString("icon"),
            weatherItem.hours
        )
        return item
    }

    private fun parseDaysWeather(body: JSONObject): List<Weather> {
        val list = ArrayList<Weather>()
        val daysArray = body.getJSONObject("forecast").getJSONArray("forecastday")
        val name = body.getJSONObject("location").getString("region")
        for (i in 0 until daysArray.length()) {
            val day = daysArray[i] as JSONObject
            val item = Weather(
                name,
                if (i == 0) body.getJSONObject("current").getString("last_updated") else day.getString("date"),
                day.getJSONObject("day").getJSONObject("condition").getString("text").trim(),
                if (i == 0 ) body.getJSONObject("current").getString("temp_c").toFloat().toInt().toString() + "°"  else "",
                day.getJSONObject("day").getString("mintemp_c").toFloat().toInt().toString() + "°",
                day.getJSONObject("day").getString("maxtemp_c").toFloat().toInt().toString() + "°",
                "https:" + day.getJSONObject("day").getJSONObject("condition").getString("icon"),
                parseHoursWeather(day.getJSONArray("hour"))
            )
            list.add(item)
        }
        return list
    }
    private fun parseHoursWeather(body: JSONArray): List<Weather> {
        val list = ArrayList<Weather>()
        for (i in 0 until body.length()) {
            val hour = body[i] as JSONObject
            val item = Weather(
                "",
                hour.getString("time").substring(10),
                hour.getJSONObject("condition").getString("text").trim(),
                hour.getString("temp_c").toFloat().toInt().toString() + "°",
                "",
                "",
                "https:" + hour.getJSONObject("condition").getString("icon"),
                listOf()
            )
            list.add(item)
        }
        return list
    }
}