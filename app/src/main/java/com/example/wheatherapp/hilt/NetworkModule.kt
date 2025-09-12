package com.example.wheatherapp.hilt

import com.example.wheatherapp.WhetherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule{
    @Singleton
    @Provides
    fun provideRepository(): Retrofit{
        return Retrofit.Builder().baseUrl("https://api.weatherapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Singleton
    @Provides
    fun provideApi(retrofit: Retrofit): WhetherApi{
        return retrofit.create(WhetherApi::class.java)
    }
}