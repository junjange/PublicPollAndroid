package com.junjange.myapplication.repository

import android.app.Application
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
}