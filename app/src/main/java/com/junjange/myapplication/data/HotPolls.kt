package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class HotPolls(@SerializedName("data") val hotPollsItem: ArrayList<HotPollsItem>)

data class HotPollsItem(
//
    @SerializedName("polls") val polls: ArrayList<HotPollsComponent>,
    @SerializedName("count") val count: ArrayList<CountItem>,


    )

data class HotPollsComponent(
    @SerializedName("contents") val contents: String,
    @SerializedName("id") val id: Int,
    @SerializedName("hasImage") val hasImage: Boolean,
    @SerializedName("presentImagePath") val presentImagePath: String?,
    @SerializedName("hashTags") val hashTags: ArrayList<HashTagsItem>,
    @SerializedName("isPublic") val isPublic: Boolean,
    @SerializedName("showNick") val showNick: Boolean,
    @SerializedName("canRevote") val canRevote: Boolean,
    @SerializedName("canComment") val canComment: Boolean,
    @SerializedName("isSingleVote") val isSingleVote: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("user") val user: UserItem,


    )

data class CountItem(
    @SerializedName("poll_id") val poll_id: Int,
    @SerializedName("cnt") val cnt: Int,

    )

