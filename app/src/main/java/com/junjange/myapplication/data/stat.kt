package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

data class Stat(@SerializedName("data") val voteItem: ArrayList<StatItem>)

data class StatItem(
    @SerializedName("itemNum") val itemNum: Int,
    @SerializedName("optionTotalCnt") val optionTotalCnt: Int,
    @SerializedName("optionItemCnt") val optionItemCnt: Int,
    @SerializedName("percent") val percent: Double,
    @SerializedName("contents") val contents: String,

    )


data class StatReqItem(
    @SerializedName("pollId") val pollId: Int,
    @SerializedName("ageOption") val ageOption: Int,
    @SerializedName("genderOption") val genderOption: Int,
    @SerializedName("tierOption") val tierOption: Int,


)
