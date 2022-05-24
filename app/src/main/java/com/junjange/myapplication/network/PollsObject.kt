package com.junjange.myapplication.network

import com.junjange.myapplication.utils.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PollsObject {


    var token: String = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjZmOGUxY2IxNTY0MTQ2M2M2ZGYwZjMzMzk0YjAzYzkyZmNjODg5YWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vcHVibGljLXBvbGwtNjcwMmEiLCJhdWQiOiJwdWJsaWMtcG9sbC02NzAyYSIsImF1dGhfdGltZSI6MTY1MzQwNDY0MiwidXNlcl9pZCI6IldhN3ZIQ281d2tORDQyQjJpTldkdWlnREtTeTIiLCJzdWIiOiJXYTd2SENvNXdrTkQ0MkIyaU5XZHVpZ0RLU3kyIiwiaWF0IjoxNjUzNDA0NjQyLCJleHAiOjE2NTM0MDgyNDIsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJ0ZXN0QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.R33qylL_j75JcljvEEjjz_xiiL8KkoIk3ZoEqkPbjzrSNpRMnMr7E1Tfidf61xK93cJj8lzRgJiQTTYHak5YTzbowPPgs54N-mv0m9lu9b0-wgjXkGAdcd6k_5qu_5H3bn_xt45E_yUGjAYoFfoxX43Ze8sA7F-M2KdDi999zEv-kDK7pKYGQZaWnW4GSt6BlYzoMquGuvs_BKlOkBG0Me7LnL3LVKpNyue7BjXr3VoGPz85VL7etyEQ0fY3WsefOLpbpCNRc-k0C53E8p-FIxFFMvYDVw5EFHwqMh090YIaXE3h9XdTMo4yHX433dkhmEDwnj1WmygPK6BYKYkL1A"
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor {
            // Request
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", "$token")
                .build()

            // Response
            val response = it.proceed(request)
            response
        }.build()


    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(API.BASE_URL1)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getRetrofitService : PollsInterface by lazy{
        getRetrofit.create(PollsInterface::class.java)
    }
}