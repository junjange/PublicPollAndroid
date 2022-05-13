package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.network.BoardObject
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
    suspend fun retrofitSelectAllTodo(): ModelBoard {
        val response = BoardObject.getRetrofitService.getBoard()
        return if (response.isSuccessful) response.body() as ModelBoard else ModelBoard(ArrayList())

    }

}