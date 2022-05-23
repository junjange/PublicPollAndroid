package com.junjange.myapplication.network

import com.junjange.myapplication.utils.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PollsObject {


    var token: String = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY5N2Q3ZmI1ZGNkZThjZDA0OGQzYzkxNThiNjIwYjY5MTA1MjJiNGQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vcHVibGljLXBvbGwtNjcwMmEiLCJhdWQiOiJwdWJsaWMtcG9sbC02NzAyYSIsImF1dGhfdGltZSI6MTY1MzMwOTk1MywidXNlcl9pZCI6IldhN3ZIQ281d2tORDQyQjJpTldkdWlnREtTeTIiLCJzdWIiOiJXYTd2SENvNXdrTkQ0MkIyaU5XZHVpZ0RLU3kyIiwiaWF0IjoxNjUzMzA5OTUzLCJleHAiOjE2NTMzMTM1NTMsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJ0ZXN0QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.gmsVrfYvJr_t6w94XW8ZeGXbkww7BAMqgYA3ahQkVUHrUNG6_cqBE3w5hnT59xESIfeGJbqlRPEbQLdeZ2zoWu6QbLJrtuYO1STvj8Bc29wTiT8k92giEPAu8OlWOuKCsp-LN8emGtmsBRqX63hnPu6ORn6Dg-VwCN1kCHRkv9g7qIcRyosYO3IcosoVyPu020BM9BUB4Ca9NCB2e-ta681Rnn5CWEb-7X1COTy8V0uQZKvsqJ4ZLWnMieAsqWv1tIF8XIRMHPRiIOVjs6Ea6H84E8W4ZDxYNRtJw1cTsFu0G9-g7vtSRIwPXVBQFneqwwAQH_vsV_Qhgm-w5jCL4Q"
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