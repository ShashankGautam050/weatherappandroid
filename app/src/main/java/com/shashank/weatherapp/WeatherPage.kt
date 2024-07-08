package com.shashank.weatherapp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.shashank.weatherapp.api.NetworkResponse
import com.shashank.weatherapp.api.WeatherResponseModel


@Composable
fun WeatherPage(viewModel: WeatherViewModel) {

    var city by remember {
        mutableStateOf("")
    }
    val weatherResult = viewModel.weatherData.observeAsState()
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 36.dp, horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                },
                label = { Text(text = "Search weather for location") },
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(fontFamily = FontFamily.SansSerif)
            )
            IconButton(
                onClick = {
                    viewModel.getData(city)
                }, modifier = Modifier
                    .height(52.dp)
                    .width(52.dp)
            ) {
                Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
            }

        }

        when (val result = weatherResult.value) {
            is NetworkResponse.Failure -> {
                Text(
                    text = result.message

                )
            }

            NetworkResponse.Loading -> CircularProgressIndicator()
            is NetworkResponse.Success -> {

                WeatherDetails(data = result.data)
            }

            null -> {

            }
        }
    }
}

@Composable
fun WeatherDetails(data: WeatherResponseModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.LocationOn, contentDescription = "Location on",
                modifier = Modifier
                    .size(40.dp)
                    .padding(horizontal = 2.dp)
            )
            Text(
                text = data.location.name + " ",
                fontSize = 24.sp,
                fontFamily = FontFamily.SansSerif,
            )
            Text(
                text = data.location.country,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray
            )


        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = data.current.temp_c + "°C",
            fontFamily = FontFamily.SansSerif,
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold
        )
        AsyncImage(
            model = "https:${data.current.condition.icon}".replace("64x64", "128x128"),
            contentDescription = "update",
            modifier = Modifier.size(106.dp)
        )
        Text(
            text = data.current.condition.text,
            fontSize = 36.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Card(elevation = CardDefaults.cardElevation(defaultElevation = 12.dp), modifier = Modifier.padding(vertical = 16.dp), shape = CardDefaults.elevatedShape) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 32.dp),
                    horizontalArrangement = Arrangement.SpaceAround

                ) {
                    WeatherForecast(key = "Humidity", value = data.current.humidity+ " %")
                    WeatherForecast(key = "Wind", value = data.current.wind_kph+ " kph")

                }
                Row ( modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.SpaceAround){
                    WeatherForecast(key = "Pressure", value = data.current.pressure_mb + " mb")
                    WeatherForecast(key = "Visibility", value = data.current.vis_km+ " km")
                }
                Row( modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    WeatherForecast(key = "Cloud", value = data.current.cloud+ " %")
                    WeatherForecast(key = "Feels like", value = data.current.feelslike_c+ " °C")

                }
            }
        }
    }
}

@Composable
fun WeatherForecast(key: String, value: String) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = key,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
        Text(
            text = value,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
        )

    }
}

@Preview
@Composable
private fun Preview() {

    WeatherPage(viewModel = WeatherViewModel())


}


