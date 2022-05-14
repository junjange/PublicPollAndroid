package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.network.BoardObject
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
    suspend fun retrofitSelectAllTodo(): ModelBoard {
        val response = BoardObject.getRetrofitService.getBoard()
        return if (response.isSuccessful) response.body() as ModelBoard else ModelBoard(ArrayList())

    }



}