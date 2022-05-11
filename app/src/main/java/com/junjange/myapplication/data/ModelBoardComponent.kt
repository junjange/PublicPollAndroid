package com.junjange.myapplication.data

import com.google.gson.annotations.SerializedName

/**
ModelBoard 와 같다.
 **/
data class ModelBoardComponent(
    @SerializedName("title") val  title: String, // 제목
    @SerializedName("contents") val  contents: String // 설명
)

