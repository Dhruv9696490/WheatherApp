package com.example.wheatherapp


import com.example.wheatherapp.weatherdata.WhetherModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val _retrofit = Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/").addConverterFactory(
    GsonConverterFactory.create()).build()
val retrofit = _retrofit.create(WhetherApi::class.java)
interface WhetherApi {
    @GET("current.json")
    suspend fun getResponse(
        @Query("key") apiKey: String,
        @Query("q") city: String
    ) : Response<WhetherModel>
}



//https://api.weatherapi.com/v1/current.json?key=b9699552c0e441b7ab7174259252407&q=Lucknow&aqi=no
//git remote add origin https://github.com/your-username/NewsApp.
//git add .
//git commit -m "Added weather forecast screen"
//git push
//dhruv