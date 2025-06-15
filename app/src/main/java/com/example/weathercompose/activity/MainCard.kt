package com.example.weathercompose.activity

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weathercompose.R
import com.example.weathercompose.model.actvity.WeatherUI
import com.example.weathercompose.ui.theme.lightBlue
import kotlinx.coroutines.launch

@Composable
fun MainCard(currentDay: MutableState<WeatherUI>, clickButton: () -> Unit, dialogSearchState: MutableState<Boolean>) {
    val offset = Offset(1.0f, 2.0f)
    Column(
        modifier = Modifier
            .padding(5.dp)
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
                        text = currentDay.value.time.replace(" ","\n"),
                        style = TextStyle(
                            fontSize = 15.sp,
                            shadow = Shadow(
                                color = Color.Black, offset = offset, blurRadius = 1f
                            )),
                        color = Color.White
                    )
                    AsyncImage(
                        model = currentDay.value.iconLink,
                        contentDescription = "weatherIcon",
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .size(50.dp)
                    )
                }
                Text(
                    text = currentDay.value.city,
                    style = TextStyle(
                        fontSize = 25.sp,
                        shadow = Shadow(
                            color = Color.Black, offset = offset, blurRadius = 1f
                        )),
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp),
                    text = if (currentDay.value.currentTemp.isEmpty())
                            if (currentDay.value.minTemp.isEmpty() || currentDay.value.maxTemp.isEmpty())
                                ""
                            else
                                "${currentDay.value.minTemp}/${currentDay.value.maxTemp}"
                        else currentDay.value.currentTemp,
                    style = TextStyle(
                        fontSize = 50.sp,
                        shadow = Shadow(
                            color = Color.Black, offset = offset, blurRadius = 1f
                        )),
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(top = 5.dp, bottom = 5.dp),
                    text = currentDay.value.condition,
                    style = TextStyle(
                        fontSize = 18.sp,
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
                            dialogSearchState.value = true
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
                        text = if (currentDay.value.currentTemp.isNotEmpty())
                            if (currentDay.value.minTemp.isNotEmpty() || currentDay.value.maxTemp.isNotEmpty())  "${currentDay.value.minTemp}/${currentDay.value.maxTemp}"
                            else ""
                        else "",
                        style = TextStyle(
                            fontSize = 22.sp,
                            shadow = Shadow(
                                color = Color.Black, offset = offset, blurRadius = 1f
                            )),
                        color = Color.White
                    )
                    IconButton(
                        onClick = {
                            clickButton.invoke()
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

@Composable
fun TabLayout(daysList: MutableState<List<WeatherUI>>, currentDay: MutableState<WeatherUI>) {
    val tabList = stringArrayResource(R.array.tab_list)
    val pagerState = rememberPagerState { tabList.size }
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.clip(RoundedCornerShape(5.dp)).padding(start = 5.dp, end = 5.dp)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { position ->
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(position[tabIndex]),
                    height = 4.dp,
                    color = Color.White
                )
            },
            containerColor = lightBlue,
            contentColor = Color.White
        )
        {
            tabList.forEachIndexed { index, text ->
            Tab(
                selected = true,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(text = text,
                        style = TextStyle(
                        fontSize = 18.sp,
                        shadow = Shadow(
                            color = Color.Black,
                            offset = Offset(1.0f, 2.0f),
                            blurRadius = 1f
                        )))
                })
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1.0f)
        ) { index ->
            val list = when(index) {
                0 -> currentDay.value.hours
                1 -> daysList.value
                else -> daysList.value
            }
            LazyColumn (
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(
                    list
                ) { index, item ->
                    ListItem(item, currentDay)
                }
            }
        }

    }
}