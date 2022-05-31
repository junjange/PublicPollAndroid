package com.junjange.myapplication.data

import android.graphics.Bitmap
import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class ItemImage (
    @SerializedName("file") val file : Bitmap,
    @SerializedName("item_num") val item_num : Int,
)