package com.example.animeviewwer

import com.example.animeviewwer.data.remote.JikanApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://api.jikan.moe/v4/"

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttppClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttppClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(JikanApi::class.java)
}