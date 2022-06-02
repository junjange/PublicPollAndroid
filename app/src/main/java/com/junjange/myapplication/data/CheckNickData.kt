package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class CheckNickData(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("resMessage") val resMessage: String,
    @SerializedName("data") val data: Boolean,
)
data class NickData(
    @SerializedName("nick") val nick: String,
)
