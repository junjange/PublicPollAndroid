package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class MyPageEdit(
    @SerializedName("email") val email : String,
    @SerializedName("nick") val nick : String,
    @SerializedName("age") val age : Int,
    @SerializedName("gender") val gender : String,
    @SerializedName("user_interest1") val user_interest1 : String,
    @SerializedName("user_interest2") val user_interest2 : String,
    @SerializedName("user_interest3") val user_interest3 : String
)
