package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class QuickPolls(@SerializedName("data") val quickPollsItem: ArrayList<QuickPollsItem>)


data class QuickPollsItem(
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: UserItem,
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
    @SerializedName("items") val items: ArrayList<ItemsComponent>,
)

data class ItemsComponent(
    @SerializedName("id") val id: Int,
    @SerializedName("poll") val poll: PollComponent,
    @SerializedName("contents") val contents: String,
    @SerializedName("hasImage") val hasImage: Boolean,

    )

data class PollComponent(
    @SerializedName("id") val id: Int,
    @SerializedName("user") val user: UserComponent,


    )

data class UserComponent(
    @SerializedName("email") val email: String,
    @SerializedName("nick") val nick: String,
    @SerializedName("age") val age: Int,
    @SerializedName("gender") val gender: String,
    @SerializedName("tier") val tier: Int,
    @SerializedName("userInterest1") val userInterest1: Int,
    @SerializedName("userInterest2") val userInterest2: Int,
    @SerializedName("userInterest3") val userInterest3: Int,

    )