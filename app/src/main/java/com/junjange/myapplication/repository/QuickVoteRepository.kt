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

        return if (response.isSuccessful) response.body() as QuickPolls else QuickPolls(ArrayList())

    }


    // post
    suspend fun retrofitPostBallot(postBallotItem: PostBallotItem): Response<Ballot> {

        return PollsObject.getRetrofitService.ballot(postBallotItem)

    }

    // reVote
    suspend fun retrofitPostReVote(postBallotItem: PostBallotItem): Response<Ballot> {
        return PollsObject.getRetrofitService.reVote(postBallotItem)

    }

}