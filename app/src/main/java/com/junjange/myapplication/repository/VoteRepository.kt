package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.google.gson.JsonObject
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.ViewPolls
import com.junjange.myapplication.data.ViewPollsItem
import com.junjange.myapplication.network.BoardObject
import com.junjange.myapplication.network.PollsObject
import java.util.*
import kotlin.collections.ArrayList

class VoteRepository(application : Application) {

    // singleton pattern
    companion object {
        private var instance: VoteRepository? = null

        fun getInstance(application : Application): VoteRepository? {
            if (instance == null) instance = VoteRepository(application)

            return instance
        }
    }

    // Use Retrofit
    suspend fun retrofitViewPolls(pollId : Int): ViewPolls? {
        val response = PollsObject.getRetrofitService.getViewPolls(pollId)


        return if (response.isSuccessful) response.body() as ViewPolls else null

    }

}