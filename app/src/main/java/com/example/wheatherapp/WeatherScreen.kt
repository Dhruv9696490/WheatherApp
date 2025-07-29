package com.example.wheatherapp

import android.content.Context
import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.wheatherapp.weatherdata.Current
import com.example.wheatherapp.weatherdata.WhetherModel

@Composable
fun WeatherScreen(context: Context,viewModel: WeatherViewModel){
    var data by remember { mutableStateOf("") }
    val weatherData = viewModel.dataState.value
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(top = 40.dp, start = 4.dp, end = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly){
            OutlinedTextField(
                value = data,
                onValueChange = {data=it},
                label = { Text("City") },
                placeholder = { Text("Enter City")},
                singleLine = true,
                modifier = Modifier
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(5.dp))
            )
            IconButton(
                onClick = {
                    if (data.isNotEmpty()){
                        viewModel.getData(data)
                    }else{
                        Toast.makeText(context,"Enter the city", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Icon(Icons.Default.Search,contentDescription = null,
                    Modifier.size(100.dp))
            }
        }
        when {
            weatherData.loading -> {
                CircularProgressIndicator()
            }
            weatherData.error != null -> {
                Text(weatherData.error.toString())
            }
            weatherData.weatherData != null -> {
                WeatherObjectScreen(weatherData.weatherData)
            }
            else -> {
                // Optional: fallback UI
                Text("Enter a city to get weather")
            }
        }
    }
}

@Composable
fun WeatherObjectScreen(weatherState: WhetherModel){
    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom){
            Icon(Icons.Default.LocationOn,null,
                Modifier.size(40.dp))
            Text(weatherState.location.name, fontSize = 30.sp)
            Spacer(Modifier.width(8.dp))
            Text(weatherState.location.country,fontSize = 20.sp,
                color = Color.Gray)
        }
        Spacer(Modifier.height(16.dp))
        Text(weatherState.current.temp_c+"°C", fontSize = 56.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center)

        AsyncImage(model ="https:${weatherState.current.condition.icon}".replace("64x64","128x128"),null,
            modifier = Modifier.size(160.dp))
        Text(weatherState.current.condition.text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Gray,
            textAlign = TextAlign.Center)
        Spacer(Modifier.height(16.dp))
        Card(modifier = Modifier.clip(shape = RoundedCornerShape(16.dp))){
            Column(Modifier.fillMaxWidth()){
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    WeatherCard(
                        value = weatherState.current.humidity,
                        key = "Humidity"
                    )
                    WeatherCard(
                        value = weatherState.current.wind_kph,
                        key = "Wind Speed"
                    )
                }
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    WeatherCard(
                        value = weatherState.current.uv,
                        key = "UV"
                    )
                    WeatherCard(
                        value = weatherState.current.precip_mm,
                        key = "Precipitation"
                    )
                }
                Row(Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround) {
                    WeatherCard(
                        value = weatherState.location.localtime.split(" ")[1],
                        key = "Time"
                    )
                    WeatherCard(
                        value = weatherState.location.localtime.split(" ")[0],
                        key = "Date"
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCard(value: String,key: String,){
    Column(modifier = Modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally){
        Text(value, fontSize = 30.sp, fontWeight = FontWeight.SemiBold)
        Text(key, fontSize = 20.sp,fontWeight = FontWeight.SemiBold, color = Color.Gray)
    }
}