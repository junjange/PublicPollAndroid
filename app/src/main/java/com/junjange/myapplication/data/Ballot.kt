package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class Ballot(@SerializedName("data") val ballotItem: ArrayList<BallotItem>)

data class BallotItem(

    @SerializedName("itemNum") val itemNum: Int,
    @SerializedName("optionTotalCnt") val optionTotalCnt: Int,
    @SerializedName("optionItemCnt") val optionItemCnt: Int,
    @SerializedName("percent") val percent: Long,
    @SerializedName("isBest") val isBest: Boolean,


    )