package com.example.wheatherapp.hilt

import com.example.wheatherapp.WhetherApi
import com.example.wheatherapp.weatherdata.WhetherModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val whetherApi: WhetherApi){
    private val _dataState = MutableStateFlow(WeatherState())
    val dataState: StateFlow<WeatherState> get()  =_dataState
    suspend fun getData(city: String){
        try{
            _dataState.value=_dataState.value.copy(
                loading = true,
            )
            val response = whetherApi.getResponse("b9699552c0e441b7ab7174259252407", city)
            if(response.body()!=null){
                _dataState.value= _dataState.value.copy(
                    loading = false,
                    weatherData = response.body()
                )
            }else{
                _dataState.value= _dataState.value.copy(
                    loading = false,
                    error = "404"
                )
            }
        }catch (e: Exception){
            _dataState.value= _dataState.value.copy(
                loading = false,
                error = "404"
            )
        }
    }
}

data class WeatherState(
    val loading: Boolean= false,
    val error: String?=null,
    val weatherData: WhetherModel? = null
)




// if(response.isSuccessful){
//                    response.body()?.let{
//                        _dataState.value=_dataState.value.copy(
//                            loading = false,
//                            weatherData = WhetherModel(
//                                current = it.current,
//                                location = it.location
//                            )
//                        )
//                    }?: run {
//                        _dataState.value=_dataState.value.copy(
//                            loading = false,
//                            error = "Null"
//                        )
//                    }
//                }else {
//                    _dataState.value = _dataState.value.copy(
//                        loading = false,
//                        error = "City Does Not Exist"
//                    )
//                }