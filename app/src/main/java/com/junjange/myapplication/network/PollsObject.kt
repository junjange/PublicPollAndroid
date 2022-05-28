package com.junjange.myapplication.network

import com.junjange.myapplication.utils.API
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object PollsObject {


    var token: String = "eyJhbGciOiJSUzI1NiIsImtpZCI6IjZmOGUxY2IxNTY0MTQ2M2M2ZGYwZjMzMzk0YjAzYzkyZmNjODg5YWMiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL3NlY3VyZXRva2VuLmdvb2dsZS5jb20vcHVibGljLXBvbGwtNjcwMmEiLCJhdWQiOiJwdWJsaWMtcG9sbC02NzAyYSIsImF1dGhfdGltZSI6MTY1MzczNDMwNSwidXNlcl9pZCI6IldhN3ZIQ281d2tORDQyQjJpTldkdWlnREtTeTIiLCJzdWIiOiJXYTd2SENvNXdrTkQ0MkIyaU5XZHVpZ0RLU3kyIiwiaWF0IjoxNjUzNzM0MzA1LCJleHAiOjE2NTM3Mzc5MDUsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZmlyZWJhc2UiOnsiaWRlbnRpdGllcyI6eyJlbWFpbCI6WyJ0ZXN0QHRlc3QuY29tIl19LCJzaWduX2luX3Byb3ZpZGVyIjoicGFzc3dvcmQifX0.tZoqzIDFAb8hTD5Ptid4hbOUXtqUWNLQMoa35e0zSGOQdWWAqbLW_mRU95EEtsXSGYvNlywprNLjpcxdQ0yPRMUvV3laj9Jp-_fW2GaiQpdpAE5UTnLL2oU414q8auDO_L4dsfNGcWXbjJ5qozBwWilDl71BtYJ_t8XN6UwnQQZ9Up_a23_8lq83C9_8XYc1kupWytNKTuRsa7lfi-SGUXUUk30LOHzufkkbyBh49lT_DIg6cz2Dh_VM5eBzmmakzZrzDZ3GsCjebQNz2y1sGndEql494mChOa2vZq9v3unVmzHyoDlkZWLmNU_WDPDUWoXXmck-rCYTbrJED7Rsyw"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.NONE
        })
        .addInterceptor {
            // Request
            val request = it.request()
                .newBuilder()
                .addHeader("Authorization", token)
                .build()

            // Response
            val response = it.proceed(request)
            response
        }.build()


    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(API.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getRetrofitService : PollsInterface by lazy{
        getRetrofit.create(PollsInterface::class.java)
    }
}