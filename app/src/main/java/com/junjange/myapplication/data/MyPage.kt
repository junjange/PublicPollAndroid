package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class MyPage(
    val statusCode: Int,
    val resMessage: String,
    val data : Data
){
    data class Data(
        @SerializedName("uid")val uid: String?,
        @SerializedName("email")val email : String,
        @SerializedName("nick") val nick : String,
        @SerializedName("age")val age : Int,
        @SerializedName("gender")val gender : String,
        @SerializedName("tier")val tier : Int,
        @SerializedName("user_interest1")val user_interest1 : String,
        @SerializedName("user_interest2")val user_interest2 : String,
        @SerializedName("user_interest3")val user_interest3 : String,
        @SerializedName("createdAt")val createdAt : Date,
        @SerializedName("updatedAt")val updatedAt : Date
    )
}

