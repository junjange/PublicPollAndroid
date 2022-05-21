package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.Polls
import com.junjange.myapplication.network.BoardObject
import com.junjange.myapplication.network.PollsObject
import java.util.ArrayList

class PollsRepository(application : Application) {

    // singleton pattern
    companion object {
        private var instance: PollsRepository? = null

        fun getInstance(application : Application): PollsRepository? {
            if (instance == null) instance = PollsRepository(application)
            return instance
        }
    }

    // Use Retrofit
    suspend fun retrofitAllPolls(): Polls {
        val response = PollsObject.getRetrofitService.getAllPolls()

        val response2 = PollsObject.getRetrofitService.getViewPolls(136)
        val response4 = PollsObject.getRetrofitService.getHashtagName("aa")
        val response5 = PollsObject.getRetrofitService.getHashtag(133)



//        Log.d("ttt", response.body().toString())
//        Log.d("ttt", response1.body().toString())
        Log.d("ttt", response2.body().toString())
        Log.d("ttt", response2.body()?.javaClass.toString())
//        Log.d("ttt", response3.body().toString())
//        Log.d("ttt", response4.body().toString())
//        Log.d("ttt", response5.body().toString())




        return if (response.isSuccessful) response.body() as Polls else Polls(ArrayList())

    }

}