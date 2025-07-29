package com.example.wheatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wheatherapp.ui.theme.WheatherAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val context = LocalContext.current
            val viewModel: WeatherViewModel = viewModel()
            WheatherAppTheme {
                WeatherScreen(context,viewModel)
            }
        }
    }
}

//b9699552c0e441b7ab7174259252407
//b9699552c0e441b7ab7174259252407

//https://api.weatherapi.com/v1/current.json?key=b9699552c0e441b7ab7174259252407&q=Lucknow&aqi=no
