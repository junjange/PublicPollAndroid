package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class Vote(@SerializedName("data") val voteItem: ArrayList<VoteItem>)

data class VoteItem(
    @SerializedName("itemNum") val itemNum: Int,
    @SerializedName("optionTotalCnt") val optionTotalCnt: Int,
    @SerializedName("optionItemCnt") val optionItemCnt: Int,
    @SerializedName("percent") val percent: Long,
    @SerializedName("isBest") val isBest: Boolean,

    )
