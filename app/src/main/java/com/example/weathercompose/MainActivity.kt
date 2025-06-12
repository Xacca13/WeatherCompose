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
import com.example.weathercompose.activity.DialogSearch
import com.example.weathercompose.activity.LocationDialog
import com.example.weathercompose.activity.MainCard
import com.example.weathercompose.activity.TabLayout
import com.example.weathercompose.activity.checkPermissions
import com.example.weathercompose.model.actvity.WeatherUI
import com.example.weathercompose.service.WeatherService
import com.example.weathercompose.ui.theme.WeatherComposeTheme
import com.google.android.gms.location.*

class MainActivity : ComponentActivity() {
    val locationService = com.example.weathercompose.activity.Location()
    val service = WeatherService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setContent {
            WeatherComposeTheme {
                val daysList = remember {
                    mutableStateOf(listOf<WeatherUI>(WeatherUI("","","","","","","",listOf())))
                }
                val currentDay = remember {
                    mutableStateOf(WeatherUI("", "", "", "", "", "", "", listOf()))
                }
                val dialogSearchState = remember {
                    mutableStateOf(false)
                }
                val dialogGeoState = remember {
                    mutableStateOf(false)
                }
                if (dialogSearchState.value) {
                    DialogSearch(
                        dialogSearchState,
                        submitCity = { service.getForecastWeather(it, daysList, currentDay) }
                    )
                }
                checkPermissions(dialogGeoState)
                if (dialogGeoState.value) {
                    LocationDialog(dialogGeoState)
                }
                locationService.getLocation(fusedLocationClient, this@MainActivity, daysList, currentDay, dialogGeoState)
                Image(
                    painter = painterResource(id = R.drawable.landscape),
                    contentDescription = "background",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .statusBarsPadding()
                        .navigationBarsPadding()
                ) {
                    MainCard(
                        currentDay,
                        clickButton = {
                            locationService.getLocation(fusedLocationClient, this@MainActivity, daysList, currentDay, dialogGeoState)
                        },
                        dialogSearchState
                    )
                    TabLayout(daysList, currentDay)
                }
            }
        }
    }
}

