package com.yeletskyiv.nitrixvideotask.retrofit

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Streaming

interface VideoApi {

    @GET("{videoName}")
    @Streaming
    suspend fun getVideos(@Path("videoName") videoName: String): Response<ResponseBody>
}