package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.HotPolls
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.network.BoardObject
import com.junjange.myapplication.network.PollsObject
import java.util.ArrayList

class HotPollsRepository(application : Application) {

    // singleton pattern
    companion object {
        private var instance: HotPollsRepository? = null

        fun getInstance(application : Application): HotPollsRepository? {
            if (instance == null) instance = HotPollsRepository(application)
            return instance
        }
    }

    // Use Retrofit
    suspend fun retrofitHotPolls(): HotPolls {
        val response = PollsObject.getRetrofitService.getHotPolls()

        return if (response.isSuccessful) response.body() as HotPolls else HotPolls(ArrayList())

    }



}