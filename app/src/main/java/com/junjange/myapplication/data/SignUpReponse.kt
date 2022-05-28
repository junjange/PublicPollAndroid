package com.junjange.myapplication.data


data class SignUpReponse(
    val statusCode: Int,
    val resMessage: String,
    val data : Data
){
    data class Data(
        val uid: String?,
        val email : String,
        val nick : String,
        val age : Int,
        val gender : String,
        val tier : Int,
        val user_interest1 : String,
        val user_interest2 : String,
        val user_interest3 : String,
    )
}
