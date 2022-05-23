package com.junjange.myapplication.network

import com.junjange.myapplication.utils.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PollsObject {


    var token: String = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjY5N2Q3ZmI1ZGNkZThjZDA0OGQzYzkxNThiNjIwYjY5MTA1MjJiNGQiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vcHVibGljLXBvbGwtNjcwMmEiLCJhdWQiOiJwdWJsaWMtcG9sbC02NzAyYSIsImF1dGhfdGltZSI6MTY1MzI4NDU2MSwidXNlcl9pZCI6IldhN3ZIQ281d2tORDQyQjJpTldkdWlnREtTeTIiLCJzdWIiOiJXYTd2SENvNXdrTkQ0MkIyaU5XZHVpZ0RLU3kyIiwiaWF0IjoxNjUzMjg0NTYxLCJleHAiOjE2NTMyODgxNjEsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJ0ZXN0QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.Y1A0ICoIdDVbzbwLjWx3baLmvqPvLvVuX_x04NYCa5BmhkkObMosrKxTtABUxdv4EEj12fE7vmZw3WLBRQiBhWyX2zbZU3_UORq9y_MZ7G-OU2mm5cAFIcgvfHVBo4TT-6QDHP7j7w8QtbiMzaF86DjPznSXzXYv1zU5OO0Rp7lWeF976Cn4XvpF_gIas90rsoTGjzTk-q8y69MIBl_qbajdojoG5pcLVGUqBm8V4hlP_IjA5jF_O8d1QZ7RFSoR5f9H3egNS8mcUflBIuQXkOFZDDpZHlasOybDjpySALh1Z_-tzk6lrFipnHo1GXgF2yhg5LP0l32pDYPYkFI-BA"

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