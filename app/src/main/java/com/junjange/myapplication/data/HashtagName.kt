package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class HashtagName(@SerializedName("data") val hashtagNameItem: ArrayList<HashtagNameItem>)

data class HashtagNameItem(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,

    )