package com.example.weathercompose.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.example.weathercompose.model.actvity.WeatherUI
import com.example.weathercompose.service.WeatherService
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.FusedLocationProviderClient

class Location() : ViewModel() {
    val service = WeatherService()

    @SuppressLint("MissingPermission")
    @OptIn(ExperimentalPermissionsApi::class)
    fun getLocation(fusedLocationClient: FusedLocationProviderClient,
                    context: Context,
                    daysList: MutableState<List<WeatherUI>>,
                    currentDay: MutableState<WeatherUI>,
                    dialogGeoState: MutableState<Boolean>) {

        if (isLocationEnabled(context)) {
            if (isLocationPermissionGranted(context)) {
                    try {
                        fusedLocationClient.lastLocation
                            .addOnSuccessListener {
                                if (it != null) {
                                    service.getForecastWeather("${it.latitude},${it.longitude}", daysList, currentDay)
                                } else {
                                    Toast.makeText(context, "Не удалось получить местоположение, введите город в ручную", Toast.LENGTH_LONG).show()
                                }
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Не удалось получить местоположение, введите город в ручную", Toast.LENGTH_LONG).show()
                            }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
            }
        } else {
            dialogGeoState.value = true
        }

    }

    fun isLocationEnabled(context: Context): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }

    fun isLocationPermissionGranted(context: Context): Boolean {
        return (ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)
                &&
                (ContextCompat.checkSelfPermission(
                    context, Manifest.permission.ACCESS_COARSE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED)

    }

}
