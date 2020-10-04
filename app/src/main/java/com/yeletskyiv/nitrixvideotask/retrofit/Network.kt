package com.yeletskyiv.nitrixvideotask.retrofit

import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun createVideoApiService(): VideoApi {
    val retrofit = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(VideoApi::class.java)
}

const val BASE_URL = "https://www.newslistener.com/Mukesh/"

val networkModule = module {
    single { createVideoApiService()}
}