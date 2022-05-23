package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.junjange.myapplication.data.MyPolls
import com.junjange.myapplication.data.Polls
import com.junjange.myapplication.network.PollsObject
import java.util.ArrayList

class MyPollsRepository (application : Application) {

    // singleton pattern
    companion object {
        private var instance: MyPollsRepository? = null

        fun getInstance(application : Application): MyPollsRepository? {
            if (instance == null) instance = MyPollsRepository(application)
            return instance
        }
    }

    // Use Retrofit
    suspend fun retrofitMyPolls(): MyPolls {
        val response = PollsObject.getRetrofitService.getMyPolls()

        return if (response.isSuccessful) response.body() as MyPolls else MyPolls(ArrayList())

    }
}