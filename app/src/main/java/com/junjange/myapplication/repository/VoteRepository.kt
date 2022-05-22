package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.junjange.myapplication.data.Comment
import com.junjange.myapplication.data.QuickPolls
import com.junjange.myapplication.data.ViewPolls
import com.junjange.myapplication.network.PollsObject
import java.util.ArrayList

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
    suspend fun retrofitViewPolls(pollId : Int): ViewPolls {
        val response = PollsObject.getRetrofitService.getViewPolls(pollId)


        Log.d("Ttt", response.body().toString())

        return response.body() as ViewPolls

    }


    suspend fun retrofitComments(pollId : Int): Comment {
        val response = PollsObject.getRetrofitService.getComments(pollId)
        Log.d("Ttt2", response.body().toString())

        return if (response.isSuccessful) response.body() as Comment else Comment(ArrayList())

    }

}