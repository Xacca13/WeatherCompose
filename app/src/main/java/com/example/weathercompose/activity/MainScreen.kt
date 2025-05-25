package com.example.weathercompose.activity

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.R
import com.example.weathercompose.ui.theme.lightBlue
import androidx.compose.ui.geometry.Offset


@Preview(showBackground = true)
@Composable
fun MainScreen() {
    val offset = Offset(1.0f, 2.0f)
    Image(painter = painterResource(id = R.drawable.landscape),
        contentDescription = "background",
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.Crop)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(5.dp)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = lightBlue),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        modifier = Modifier.padding(top = 15.dp, start = 5.dp),
                        text = "20 May 2025 14:00",
                        style = TextStyle(
                            fontSize = 15.sp,
                            shadow = Shadow(
                                color = Color.Black, offset = offset, blurRadius = 1f
                            )),
                        color = Color.White
                    )
                    AsyncImage(
                        model = R.drawable.cloudy,
                        contentDescription = "weatherIcon",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(50.dp)
                    )
                }
                Text(
                    text = "Omsk",
                    style = TextStyle(
                        fontSize = 25.sp,
                        shadow = Shadow(
                            color = Color.Black, offset = offset, blurRadius = 1f
                        )),
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = "23C",
                    style = TextStyle(
                        fontSize = 50.sp,
                        shadow = Shadow(
                            color = Color.Black, offset = offset, blurRadius = 1f
                        )),
                    color = Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.search_icon),
                            contentDescription = "Search_icon",
                            tint = Color.White
                        )
                    }
                    Text(
                        modifier = Modifier.padding(top = 5.dp),
                        text = "Sunny",
                        style = TextStyle(
                            fontSize = 18.sp,
                            shadow = Shadow(
                                color = Color.Black, offset = offset, blurRadius = 1f
                            )),
                        color = Color.White
                    )
                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.cloud_sync),
                            contentDescription = "Sync_icon",
                            tint = Color.White
                        )
                    }
                }
            }

        }
    }
}