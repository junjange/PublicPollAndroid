package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName
import java.util.*

data class NewPoll(
    @SerializedName("contents") val contents : String,
    @SerializedName("hashTags") val hashTags : ArrayList<String>,
    @SerializedName("endTime") val endTime : String,
    @SerializedName("hasImage") val hasImage : Boolean,
    @SerializedName("isPublic") val isPublic : Boolean,
    @SerializedName("showNick") val showNick : Boolean,
    @SerializedName("canRevote") val canRevote : Boolean,
    @SerializedName("canComment") val canComment : Boolean,
    @SerializedName("isSingleVote") val isSingleVote : Boolean,
    @SerializedName("items") val items : ArrayList<Item>,
){
    data class Item(
        @SerializedName("itemNum")val itemNum: Int,
        @SerializedName("contents")val contents: String,
        @SerializedName("hasImage")val hasImage: Boolean,
    )
}
