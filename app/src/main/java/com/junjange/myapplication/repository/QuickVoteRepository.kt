package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.network.BoardObject
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
    suspend fun retrofitSelectAllTodo(): ModelBoard {
        val response = BoardObject.getRetrofitService.getBoard()
        return if (response.isSuccessful) response.body() as ModelBoard else ModelBoard(ArrayList())

    }
}