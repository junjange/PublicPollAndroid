package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.junjange.myapplication.data.MyBallot
import com.junjange.myapplication.data.MyPolls
import com.junjange.myapplication.network.PollsObject
import java.util.ArrayList

class MyBallotRepository (application : Application) {

    // singleton pattern
    companion object {
        private var instance: MyBallotRepository? = null

        fun getInstance(application : Application): MyBallotRepository? {
            if (instance == null) instance = MyBallotRepository(application)
            return instance
        }
    }

    // Use Retrofit
    suspend fun retrofitMyBallot(): MyBallot {
        val response = PollsObject.getRetrofitService.getMyBallot()

        Log.d("ttt", response.body().toString())

        return if (response.isSuccessful) response.body() as MyBallot else MyBallot(ArrayList())

    }
}