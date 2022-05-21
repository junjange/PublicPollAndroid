package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class ViewPolls(@SerializedName("data") val viewPollsItem: ViewPollsItem)

data class ViewPollsItem(
    @SerializedName("id") val id: Int,
    @SerializedName("nick") val nick: String,
    @SerializedName("tier") val tier: Int,
    @SerializedName("contents") val contents: String,
    @SerializedName("hashTags") val hashTags: ArrayList<HashTagsItem>,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("hasImage") val hasImage: Boolean,
    @SerializedName("isPublic") val isPublic: Boolean,
    @SerializedName("showNick") val showNick: Boolean,
    @SerializedName("canRevote") val canRevote: Boolean,
    @SerializedName("canComment") val canComment: Boolean,
    @SerializedName("isSingleVote") val isSingleVote: Boolean,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("items") val items: ArrayList<ItemComponent>,
    @SerializedName("myBallots") val myBallots: ArrayList<Int>,
    @SerializedName("stats") val stats: ArrayList<String>,

    )

data class ItemComponent(
    @SerializedName("id") val id: Int,
    @SerializedName("poll") val poll: ArrayList<PollItem>,
    @SerializedName("itemNum") val itemNum: Int,
    @SerializedName("contents") val contents: String,
    @SerializedName("hasImage") val hasImage: Boolean,

    )

data class PollItem(
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: ArrayList<UserComponent>,
    @SerializedName("contents") val contents: String,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("hasImage") val hasImage: Boolean,
    @SerializedName("presentImagePath") val presentImagePath: String,
    @SerializedName("isPublic") val isPublic: Boolean,
    @SerializedName("showNick") val showNick: Boolean,
    @SerializedName("canRevote") val canRevote: Boolean,
    @SerializedName("canComment") val canComment: Boolean,
    @SerializedName("isSingleVote") val isSingleVote: Boolean,
    @SerializedName("createdAt") val createdAt: String,

    )

