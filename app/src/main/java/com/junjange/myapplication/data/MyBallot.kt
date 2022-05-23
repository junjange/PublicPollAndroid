package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class MyBallot(@SerializedName("data") val pollsItem: ArrayList<MyBallotItem>)

data class MyBallotItem(
    @SerializedName("poll") val poll: ArrayList<MyPollsComponent>,

)

data class MyPollsComponent(
    @SerializedName("contents") val contents: String,
    @SerializedName("id") val id: Int,
    @SerializedName("endTime") val endTime: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("hasImage") val hasImage: Boolean,
    @SerializedName("presentImagePath") val presentImagePath: Boolean?,
    @SerializedName("hashTags") val hashTags: ArrayList<HashTagsItem>,
    @SerializedName("isPublic") val isPublic: Boolean,
    @SerializedName("showNick") val showNick: Boolean,
    @SerializedName("canRevote") val canRevote: Boolean,
    @SerializedName("canComment") val canComment: Boolean,
    @SerializedName("isSingleVote") val isSingleVote: Boolean ,
)
