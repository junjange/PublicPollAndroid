package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class NewPollResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("resMessage") val resMessage: String,
    @SerializedName("data") val data: Data,
) {
    data class Data(
        @SerializedName("contents") val contents: String,
        @SerializedName("id") val id: Int,
        @SerializedName("endTime") val endTime: String,
        @SerializedName("user") val user: User,
        @SerializedName("hasImage") val hasImage: Boolean,
        @SerializedName("isPublic") val isPublic: Boolean,
        @SerializedName("showNick") val showNick: Boolean,
        @SerializedName("canRevote") val canRevote: Boolean,
        @SerializedName("canComment") val canComment: Boolean,
        @SerializedName("isSingleVote") val isSingleVote: Boolean,
        @SerializedName("createdAt") val createdAt: String,
        @SerializedName("hashTags") val hashTags: List<HashTag>
    ) {
        data class User(
            @SerializedName("nick") val nick: String,
            @SerializedName("tier") val tier: Int,
        )
        data class HashTag(
            @SerializedName("name") val name: String,
            @SerializedName("id") val id: Int,
        )
    }
}

