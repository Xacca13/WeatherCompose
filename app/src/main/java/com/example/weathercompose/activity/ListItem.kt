package com.example.weathercompose.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.model.actvity.Weather
import com.example.weathercompose.ui.theme.lightBlue

@Composable
fun ListItem(item: Weather) {
    val offset = Offset(1.0f, 2.0f)
    Card (
        modifier = Modifier.fillMaxWidth()
            .padding(top = 3.dp),
        colors = CardDefaults.cardColors(containerColor = lightBlue),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        shape = RoundedCornerShape(5.dp)
    ){
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.padding(start = 5.dp, top = 5.dp, bottom = 5.dp).weight(0.25f)
            ) {
                Text(
                    modifier = Modifier.padding(bottom = 3.dp),
                    text = item.time,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 15.sp,
                        shadow = Shadow(
                            color = Color.Black, offset = offset, blurRadius = 1f
                        ))
                )
                Text(
                    text = item.condition.replace(" ", "\n"),
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 15.sp,
                        shadow = Shadow(
                            color = Color.Black, offset = offset, blurRadius = 1f
                        ))
                )
            }
            Text(
                modifier = Modifier.padding(top = 5.dp).weight(0.25f),
                text = item.currentTemp.ifEmpty {"${item.minTemp}/${item.maxTemp}"},
                color = Color.White,
                style = TextStyle(
                    fontSize = 25.sp,
                    shadow = Shadow(
                        color = Color.Black, offset = offset, blurRadius = 1f
                    ))
            )
            AsyncImage(
                model = item.iconLink,
                contentDescription = "weatherIconTab",
                modifier = Modifier
                    .size(45.dp)
            )
        }
    }
}