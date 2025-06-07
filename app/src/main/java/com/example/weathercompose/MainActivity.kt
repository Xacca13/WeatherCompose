package com.example.weathercompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.weathercompose.activity.MainCard
import com.example.weathercompose.activity.TabLayout
import com.example.weathercompose.model.actvity.Weather
import com.example.weathercompose.service.WeatherService
import com.example.weathercompose.ui.theme.WeatherComposeTheme

class MainActivity : ComponentActivity() {
    val service = WeatherService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherComposeTheme {
                val daysList = remember {
                    mutableStateOf(listOf<Weather>(Weather("","","","","","","",listOf())))
                }
                service.requestWeatherData("Omsk", this, daysList)
                Image(painter = painterResource(id = R.drawable.landscape),
                    contentDescription = "background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop)
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    MainCard(daysList)
                    TabLayout(daysList)
                }
            }
        }
    }
}

