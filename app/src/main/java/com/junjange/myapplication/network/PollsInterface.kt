package com.junjange.myapplication.network

import com.google.gson.JsonObject
import com.junjange.myapplication.data.*
import com.junjange.myapplication.utils.API
import retrofit2.Response
import retrofit2.http.*
import kotlin.collections.ArrayList

interface PollsInterface {

    // 전체 투표 조회
    @GET(API.GET_ALL_POLLS)
    suspend fun getAllPolls(): Response<Polls>

    // 핫 투표 조회
    @GET(API.GET_HOT_POLLS)
    suspend fun getHotPolls(): Response<HotPolls>

    // 투표 보기
    @GET("poll/{pollId}")
    suspend fun getViewPolls(
        @Path("pollId") pollId : Int
    ): Response<ViewPolls>

    // 빠른 투표 보기
    @GET(API.GET_QUICK_POLLS)
    suspend fun getQuickPolls(): Response<QuickPolls>

    // 해시태그 이름 검색
    @GET("hashtag/name/{keyword}")
    suspend fun getHashtagName(
        @Path("keyword") keyword : String
    ): Response<HashtagName>

    // 해시태그 검색
    @GET("hashtag/id/{hashtagId}")
    suspend fun getHashtag(
        @Path("hashtagId") hashtagId : Int
    ): Response<Hashtag>

    // 댓글 검색
    @GET("comment/id/{pollId}")
    suspend fun getComments(
        @Path("pollId") pollId : Int
    ): Response<Comment>

    // 댓글 하기
    @FormUrlEncoded
    @POST("comment/add")
    suspend fun postComment(
        @Field("pollId") pollId: Int,
        @Field("contents") contents: String

    ): Response<JsonObject>


    // 투표 하기
    @FormUrlEncoded
    @POST("ballot/add")
    suspend fun ballot(
        @Field("pollId") pollId: Int,
        @Field("contents") contents: ArrayList<Int>

    ): Response<Ballot>

    // My Polls
    @GET("poll/my")
    suspend fun getMyPolls(): Response<MyPolls>

    // My Ballot
    @GET("ballot/my")
    suspend fun getMyBallot(): Response<MyBallot>



}