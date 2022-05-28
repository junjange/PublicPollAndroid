package com.junjange.myapplication.network

import com.google.gson.JsonObject
import com.junjange.myapplication.data.*
import com.junjange.myapplication.utils.API
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*
import retrofit2.http.Body

import retrofit2.http.POST




interface PollsInterface {
    @POST(API.POST_SIGN_UP)  //연산 지정(post,get 등등)
    fun postSignUp( //body로 들어갈 필드 지정
        @Header("Authorization") idToken: String,
        @Body signUpData: SignUp
    ): Call<SignUpReponse>

    @GET(API.GET_POST_MYPAGE)
    fun getMyPageGet (
        @Header("Authorization") idToken: String
    ): Call<MyPage>

    @POST(API.GET_POST_MYPAGE)
    fun postMyPageEdit (
        @Header("Authorization") idToken: String,
        @Body myPageEditData: MyPageEdit
    ): Call<SignUpReponse>

    // 전체 투표 조회
    @GET(API.GET_ALL_POLLS)
    suspend fun getAllPolls(): Response<Polls>

    // 핫 투표 조회
    @GET(API.GET_HOT_POLLS)
    suspend fun getHotPolls(): Response<HotPolls>

    // 투표 보기
    @GET(API.GET_VIEW_POLLS)
    suspend fun getViewPolls(
        @Path("pollId") pollId : Int
    ): Response<ViewPolls>

    // 빠른 투표 보기
    @GET(API.GET_QUICK_POLLS)
    suspend fun getQuickPolls(): Response<QuickPolls>

    // 해시태그 이름 검색
    @GET(API.GET_HASHTAG_NAME)
    suspend fun getHashtagName(
        @Path("keyword") keyword : String
    ): Response<HashtagName>

    // 해시태그 검색
    @GET(API.GET_HASHTAG)
    suspend fun getHashtag(
        @Path("hashtagId") hashtagId : Int
    ): Response<Hashtag>

    // 댓글 검색
    @GET(API.GET_COMMENT)
    suspend fun getComments(
        @Path("pollId") pollId : Int
    ): Response<Comment>

    // 댓글 하기
    @POST(API.POST_COMMENT)
    suspend fun postComment(
        @Body postCommentItem: PostCommentItem
    ): Response<JsonObject>


    // 투표 하기
    @POST(API.POST_BALLOT)
    suspend fun ballot(
        @Body postBallotItem: PostBallotItem

    ): Response<Ballot>

    // 재투표 하기
    @POST(API.POST_REVOTE)
    suspend fun reVote(
        @Body postBallotItem: PostBallotItem

    ): Response<Ballot>

    // 투표 삭제하기
    @DELETE(API.DELETE_POLLS)
    suspend fun pollDelete(): Response<JsonObject>

    // My Polls
    @GET(API.MY_POLLS)
    suspend fun getMyPolls(): Response<MyPolls>

    // My Ballot
    @GET(API.MY_BALLOT)
    suspend fun getMyBallot(): Response<MyBallot>



}