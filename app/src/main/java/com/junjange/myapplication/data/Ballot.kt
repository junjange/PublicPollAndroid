package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class Ballot(@SerializedName("data") val ballotItem: ArrayList<BallotItem>)

data class BallotItem(

    @SerializedName("itemNum") val itemNum: Int,
    @SerializedName("optionTotalCnt") val optionTotalCnt: Int,
    @SerializedName("optionItemCnt") val optionItemCnt: Int,
    @SerializedName("percent") val percent: Double,
    @SerializedName("isBest") val isBest: Boolean,


    )

data class PostBallotItem(
    @SerializedName("pollId") val  pollId: Int, // 제목
    @SerializedName("itemNum") val  itemNum: ArrayList<Int> // 설명
)

