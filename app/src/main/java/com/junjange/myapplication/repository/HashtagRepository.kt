package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.*
import com.junjange.myapplication.network.PollsObject
import java.util.ArrayList

class HashtagRepository (application : Application) {

    // singleton pattern
    companion object {
        private var instance: HashtagRepository? = null

        fun getInstance(application : Application): HashtagRepository? {
            if (instance == null) instance = HashtagRepository(application)
            return instance
        }
    }

    // Use Retrofit
    suspend fun retrofitHashtag(id : Int): Hashtag {
        val response = PollsObject.getRetrofitService.getHashtag(id)

        return response.body() as Hashtag

    }
}