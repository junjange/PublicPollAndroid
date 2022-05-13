package com.junjange.myapplication.network

import com.google.gson.JsonObject
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.utils.API
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

/**
Rest API 서버와 통신하는 방법을 정의한 인터페이스

Call같은 경우는 명시적으로 Success / Fail을 나눠서 처리
Response 같은 경우는 서버에서 Status Code를 받아서 케이스를 나눠 처리
Callback Hell을 방지하려면 Response를 이용해서 하는 것이 더 좋다고 한다.
>>>>>>>>> 이 부분은 더 알아봐야할듯.
 **/

// 결과 xml 파일에 접근해서 정보 가져오기
interface BoardInterface {
    @GET(API.GET_POST)
    suspend fun getBoard(): Response<ModelBoard>

    @FormUrlEncoded
    @POST(API.ADD_POST)
    fun postBoard(
        @Field("title") title: String,
        @Field("contents") contents: String
    ): Call<JsonObject>
}




