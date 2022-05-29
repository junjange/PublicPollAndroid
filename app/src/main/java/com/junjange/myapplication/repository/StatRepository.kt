package com.junjange.myapplication.repository

import android.app.Application
import com.junjange.myapplication.data.Stat
import com.junjange.myapplication.data.StatReqItem
import com.junjange.myapplication.network.PollsObject
import retrofit2.Response

class StatRepository (application : Application) {

    // singleton pattern
    companion object {
        private var instance: StatRepository? = null
        fun getInstance(application: Application): StatRepository? {
            if (instance == null) instance = StatRepository(application)
            return instance
        }
    }

    suspend fun retrofitStat(statReqItem: StatReqItem): Response<Stat> {
        return PollsObject.getRetrofitService.getStat(statReqItem)
    }

}