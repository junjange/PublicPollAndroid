package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.google.gson.JsonObject
import com.junjange.myapplication.data.*
import com.junjange.myapplication.network.PollsObject
import retrofit2.Response
import java.util.ArrayList

class QuickVoteRepository(application : Application) {


    // singleton pattern
    companion object {
        private var instance: QuickVoteRepository? = null

        fun getInstance(application : Application): QuickVoteRepository? {
            if (instance == null) instance = QuickVoteRepository(application)
            return instance
        }
    }

    // Use Retrofit
    suspend fun retrofitQuickPolls(): QuickPolls {
        val response = PollsObject.getRetrofitService.getQuickPolls()

        Log.d("tt1", response.body().toString())

        return if (response.isSuccessful) response.body() as QuickPolls else QuickPolls(ArrayList())

    }

    // post
    suspend fun retrofitPostBallot(postBallotItem: PostBallotItem): Response<Ballot> {

        var a = PollsObject.getRetrofitService.ballot(postBallotItem)
        Log.d("ttt", a.toString())
        return a

    }


}