package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class HotPolls(@SerializedName("data") val hotPollsItem: ArrayList<HotPollsItem>)

data class HotPollsItem(
    @SerializedName("contents") val contents: String,
    @SerializedName("polls") val polls: ArrayList<HotPollsComponent>,
    @SerializedName("count") val count: ArrayList<CountItem>,


    )

data class HotPollsComponent(
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
    @SerializedName("presentImagePath") val presentImagePath: String,
    @SerializedName("hashTags") val hashTags: ArrayList<HashTagsItem>,


    )

data class CountItem(
    @SerializedName("poll_id") val poll_id: Int,
    @SerializedName("cnt") val cnt: Int,

    )

