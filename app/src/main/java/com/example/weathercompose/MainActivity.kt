package com.example.weathercompose

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.weathercompose.activity.MainScreen
import com.example.weathercompose.ui.theme.WeatherComposeTheme
import org.json.JSONObject

private const val API_KEY: String = "3204d89db56b4704a5f91432252303"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherComposeTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun Greeting(name: String, context: Context) {
    val state = remember{
        mutableStateOf("unknown")
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()) {
        Box(modifier = Modifier
            .fillMaxHeight(0.9f)
            .fillMaxWidth(),
            contentAlignment = Alignment.Center) {
            Text(text = state.value,
                fontSize = 32.sp)
        }
        Box(modifier = Modifier
            .fillMaxWidth(),
            contentAlignment = Alignment.BottomCenter) {
            Button(onClick = {
                getResult(name, state, context)
            }, modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()) {
                Text(text = "Refresh")
            }
        }
    }
}

private fun getResult(city: String, state: MutableState<String>, context: Context) {
    val link = "https://api.weatherapi.com/v1/forecast.json" +
            "?key=$API_KEY" +
            "&q=$city" +
            "&days=3" +
            "&aqi=no" +
            "&alerts=no"
    val req = StringRequest(
        Request.Method.GET,
        link,
        { response ->
            val res = JSONObject(response).getJSONObject("current").getString("temp_c").toFloat().toInt().toString() + "°"
            state.value = res
        },
        { err -> Log.e("MyLog", "Error: $err")}
    )
    val queue = Volley.newRequestQueue(context)
    queue.add(req)
}
