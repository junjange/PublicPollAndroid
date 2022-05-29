package com.junjange.myapplication.repository

import android.app.Application
import android.util.Log
import com.google.gson.JsonObject
import com.junjange.myapplication.data.*
import com.junjange.myapplication.network.PollsObject
import retrofit2.Response
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

    // 내가 만든 투표
    suspend fun retrofitMyPolls(): MyPolls {
        val response = PollsObject.getRetrofitService.getMyPolls()

        return if (response.isSuccessful) response.body() as MyPolls else MyPolls(ArrayList())

    }

    // 내 투표
    suspend fun retrofitViewPolls(pollId : Int): ViewPolls {
        val response = PollsObject.getRetrofitService.getViewPolls(pollId)
        Log.d("tttview", response.body().toString())

        return response.body() as ViewPolls

    }

    // post
    suspend fun retrofitPostBallot(postBallotItem: PostBallotItem): Response<Ballot> {
        return PollsObject.getRetrofitService.ballot(postBallotItem)

    }

    // reVote
    suspend fun retrofitPostReVote(postBallotItem: PostBallotItem): Response<Ballot> {
        return PollsObject.getRetrofitService.reVote(postBallotItem)

    }

    suspend fun retrofitDeleteReVote(pollId : Int): Response<JsonObject> {
        return PollsObject.getRetrofitService.pollDelete(pollId)

    }


    suspend fun retrofitComments(pollId : Int): Comment {
        val response = PollsObject.getRetrofitService.getComments(pollId)

        return if (response.isSuccessful) response.body() as Comment else Comment(ArrayList())

    }

    // Post
    suspend fun retrofitPostComments(postCommentItem: PostCommentItem) : Response<JsonObject> {
        return PollsObject.getRetrofitService.postComment(postCommentItem)
    }

}