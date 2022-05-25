package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.Polls
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

        return if (response.isSuccessful) response.body() as Polls else Polls(ArrayList())

    }

}