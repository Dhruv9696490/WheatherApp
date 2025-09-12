package com.example.wheatherapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.hilt.WeatherRepository
import com.example.wheatherapp.hilt.WeatherState
import com.example.wheatherapp.weatherdata.WhetherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val weatherRepository: WeatherRepository): ViewModel(){
    val dataState: StateFlow<WeatherState> = weatherRepository.dataState
    fun getWeatherData(city: String){
        viewModelScope.launch {
            weatherRepository.getData(city)
        }
    }

}
