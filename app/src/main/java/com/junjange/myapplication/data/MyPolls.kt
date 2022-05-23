package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class MyPolls(@SerializedName("data") val pollsItem: ArrayList<MyPollsItem>)

data class MyPollsItem(
    @SerializedName("contents") val contents: String,
    @SerializedName("id") val id: Int,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("user") val user: UserItem,
    @SerializedName("hasImage") val hasImage: Boolean,
    @SerializedName("isPublic") val isPublic: Boolean,
    @SerializedName("showNick") val showNick: Boolean,
    @SerializedName("canRevote") val canRevote: Boolean,
    @SerializedName("canComment") val canComment: Boolean,
    @SerializedName("isSingleVote") val isSingleVote: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("presentImagePath") val presentImagePath: String?,
    @SerializedName("hashTag") val hashTags: ArrayList<HashTagsItem>,
)

