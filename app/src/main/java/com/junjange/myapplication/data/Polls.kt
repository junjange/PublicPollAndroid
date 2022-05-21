package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class Polls(@SerializedName("data") val pollsItem: ArrayList<PollsItem>)

data class PollsItem(
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
    @SerializedName("presentImagePath") val presentImagePath: String,
    @SerializedName("hashTag") val hashTags: ArrayList<HashTagsItem>,
)

data class UserItem(
    @SerializedName("nick") val nick: String,
    @SerializedName("tier") val tier: Int,

    )

data class HashTagsItem(
    @SerializedName("name") val name: String,
    @SerializedName("id") val id: Int,

    )