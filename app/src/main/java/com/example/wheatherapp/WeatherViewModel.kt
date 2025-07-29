package com.example.wheatherapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wheatherapp.weatherdata.WhetherModel
import kotlinx.coroutines.launch

class WeatherViewModel: ViewModel(){
    private val _dataState = mutableStateOf(WeatherState())
    val dataState: State<WeatherState> =_dataState
    fun getData(city: String){
        viewModelScope.launch {
            _dataState.value=_dataState.value.copy(
                loading = true,
                error = null,
            )
            try {
                val response = retrofit.getResponse("b9699552c0e441b7ab7174259252407", city)
                if(response.isSuccessful){
                        response.body()?.let{
                            _dataState.value=_dataState.value.copy(
                                loading = false,
                                weatherData = WhetherModel(
                                    current = it.current,
                                    location = it.location
                                )
                            )
                        }?: run {
                            _dataState.value=_dataState.value.copy(
                                loading = false,
                                error = "Null"
                            )
                        }
                }else{
                    _dataState.value=_dataState.value.copy(
                        loading = false,
                        error = "City Does Not Exist"
                    )
                }
            }catch(e: Exception){
                _dataState.value=_dataState.value.copy(
                    loading = false,
                    error = "Unnecessary Error"
                )
            }
        }
    }
}
data class WeatherState(
    val loading: Boolean= false,
    val error: String?=null,
    val weatherData: WhetherModel? = null
)