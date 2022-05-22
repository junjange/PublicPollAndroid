package com.junjange.myapplication.network

import com.google.gson.JsonObject
import com.junjange.myapplication.data.*
import com.junjange.myapplication.utils.API
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.*

interface PollsInterface {

    // 전체 투표 조회
    @GET(API.GET_ALL_POLLS)
    suspend fun getAllPolls(): Response<Polls>

    // 핫 투표 조회
    @GET(API.GET_HOT_POLLS)
    suspend fun getHotPolls(): Response<JsonObject>

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






}