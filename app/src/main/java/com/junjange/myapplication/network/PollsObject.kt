package com.junjange.myapplication.network

import com.junjange.myapplication.utils.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PollsObject {


    var token: String = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY5N2Q3ZmI1ZGNkZThjZDA0OGQzYzkxNThiNjIwYjY5MTA1MjJiNGQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vcHVibGljLXBvbGwtNjcwMmEiLCJhdWQiOiJwdWJsaWMtcG9sbC02NzAyYSIsImF1dGhfdGltZSI6MTY1MzE0MDE2MiwidXNlcl9pZCI6IldhN3ZIQ281d2tORDQyQjJpTldkdWlnREtTeTIiLCJzdWIiOiJXYTd2SENvNXdrTkQ0MkIyaU5XZHVpZ0RLU3kyIiwiaWF0IjoxNjUzMTQwMTYyLCJleHAiOjE2NTMxNDM3NjIsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJ0ZXN0QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.nkDKZNYncvMS-z7NSdXYd4pN8LG5R8m1grWQ5oSGhm8fQngoeUN8bHCih_5M4zwbbcnVTdxa_J1xZrzJJR8Dcbq-49LOPD1Qvg3l4md4zNmTkv9LxnWaC-fOQ-wPybH9dXHDZHR0t6pbJBzFxv3FCva8ZS9Di3lz0ULUIDc2_HJuMlQdcjNvcOj9D-ToLzGSkk-QmFOyngu3xjeVn2K5FCDDgUhhR-RQpUpVfSJkFV9yvwWd_5uFkduahQ8Hsw01Z_2IC0qOvPNPqsmXu67OQdVtJY0O-Cji65Kf9yWg9qNJpenhrGL-MLzf2WVC2QUdoWaQ8bXbBUo9aJta7uHnaQ"
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