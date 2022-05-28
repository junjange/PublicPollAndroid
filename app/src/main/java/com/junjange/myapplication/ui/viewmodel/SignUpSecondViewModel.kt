package com.junjange.myapplication.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.junjange.myapplication.data.SignUp
import com.junjange.myapplication.data.SignUpReponse
import com.junjange.myapplication.network.PollsObject
import retrofit2.Call
import retrofit2.Response

class SignUpSecondViewModel() : ViewModel() {

    fun signUp(idToken : String, email : String, nickname : String, age : Int, gender : String, userInterest : Array<String>, activity: Activity) {
        val service = PollsObject.getRetrofitService
        val call = service.postSignUp(idToken, SignUp(email, nickname, age, gender, tier = 1, userInterest[0], userInterest[1], userInterest[2]))

        call.enqueue(object : retrofit2.Callback<SignUpReponse> {
            override fun onResponse(
                call: Call<SignUpReponse>,
                response: Response<SignUpReponse>
            ) {
                Log.d("성공", response.body().toString())
            }

            override fun onFailure(call: Call<SignUpReponse>, t: Throwable) {
                Log.d("실패", ";ㅁ;")
            }
        })
    }
}