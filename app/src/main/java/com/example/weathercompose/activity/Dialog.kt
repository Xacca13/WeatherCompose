package com.example.weathercompose.activity

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.weathercompose.R

@Composable
fun DialogSearch(dialogSearchState: MutableState<Boolean>, submitCity: (String) -> Unit) {
    val textSearch = remember {
        mutableStateOf("")
    }
    AlertDialog(onDismissRequest = {
        dialogSearchState.value = false
    },
        confirmButton = {
            TextButton(onClick = {
                if (textSearch.value.isNotEmpty()) submitCity(textSearch.value)
                dialogSearchState.value = false
            }) {
                Text(text = stringResource(R.string.dialog_search_ok))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogSearchState.value = false
            }) {
                Text(text = stringResource(R.string.dialog_search_cancel))
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.dialog_search_input_row))
                TextField(
                    value = textSearch.value,
                    onValueChange = {
                        textSearch.value = it
                    })
            }
        }
    )
}

@Composable
fun LocationDialog(dialogGeoState: MutableState<Boolean>) {
    val context = LocalContext.current
    AlertDialog(
        onDismissRequest = {
            dialogGeoState.value = false
        },
        confirmButton = {
            TextButton(onClick = {
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }) {
                Text(text = "Перейти к настройкам")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                dialogGeoState.value = false
            }) {
                Text(text = "Отмена")
            }
        },
        title = {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Включите местоположение")
            }
        },
        text = {
            Text(text = "Для использования приложения необходимо включить геолокацию.")
        }
    )
}