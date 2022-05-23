package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.junjange.myapplication.data.Ballot
import com.junjange.myapplication.data.HashtagName
import com.junjange.myapplication.data.QuickPolls
import com.junjange.myapplication.network.PollsObject
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

    // Use Retrofit
    suspend fun retrofitBallot(pollId : Int, itemNum : ArrayList<Int>): Ballot {
        val response = PollsObject.getRetrofitService.ballot(pollId, itemNum)

        return if (response.isSuccessful) response.body() as Ballot else Ballot(ArrayList())

    }


}