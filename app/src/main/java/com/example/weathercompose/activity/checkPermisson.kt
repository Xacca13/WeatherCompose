package com.example.weathercompose.activity

import android.Manifest
import android.content.Context
import android.location.LocationManager
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun checkPermissions(dialogGeoState: MutableState<Boolean>): Boolean {
    var result = false
    LocationCheckScreen(dialogGeoState)

    if (!dialogGeoState.value) {
        val permissionsState = rememberMultiplePermissionsState(
            permissions = listOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        LaunchedEffect(Unit) {
            permissionsState.launchMultiplePermissionRequest()
        }

    }

    return result
}

@Composable
fun LocationCheckScreen(dialogGeoState: MutableState<Boolean>) {
    val context = LocalContext.current
    var isLocationEnabled = isLocationEnabled(context)

    LaunchedEffect(isLocationEnabled) {
        if (!isLocationEnabled) {
            dialogGeoState.value = true
        }
    }
}

fun isLocationEnabled(context: Context): Boolean {
    val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
            locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
}

