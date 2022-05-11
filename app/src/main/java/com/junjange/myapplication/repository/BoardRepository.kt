package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.network.BoardObject
import java.util.ArrayList


/**
ViewMovel에서는 로컬데이터인지 원격데이터인지 신경쓰지않고 Repository를 사용할 수 있다.
result.isSuccessful : 통신에 성공했는지의 여부. 이때의 통신은 갔다 왔는가 그자체를 의미하는 것
result.body : 실질적으로 받게되는 데이터입니다. `as Type`으로 객체 타입을 명시. 여기서는 ModelBoard를 받음.
 **/


class BoardRepository(application : Application) {

    // Use Retrofit
    suspend fun retrofitSelectAllTodo(): ModelBoard {
        val response = BoardObject.getRetrofitService.getBoard()
        return if (response.isSuccessful) response.body() as ModelBoard else ModelBoard(ArrayList())

    }
    // singleton pattern
    companion object {
        private var instance: BoardRepository? = null

        fun getInstance(application : Application): BoardRepository? {
            if (instance == null) instance = BoardRepository(application)
            return instance
        }
    }
}