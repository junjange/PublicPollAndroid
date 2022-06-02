package com.junjange.myapplication.ui.viewmodel

import android.app.Activity
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.junjange.myapplication.data.CheckNickData
import com.junjange.myapplication.data.NickData
import com.junjange.myapplication.data.SignUp
import com.junjange.myapplication.data.SignUpReponse
import com.junjange.myapplication.databinding.ActivitySignUpSecondBinding
import com.junjange.myapplication.network.PollsObject
import retrofit2.Call
import retrofit2.Response

class SignUpSecondViewModel() : ViewModel() {
    fun signUp(email : String, nickname : String, age : Int, gender : String, userInterest : Array<String>, activity: Activity) {
        val service = PollsObject.getRetrofitService
        val call = service.postSignUp(SignUp(email, nickname, age, gender, tier = 1, userInterest[0], userInterest[1], userInterest[2]))

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

    fun checkNick(nick : String, binding: ActivitySignUpSecondBinding){
        val service = PollsObject.getRetrofitService
        val call = service.postCheckNick(NickData(nick))
        var flag = false

        call.enqueue(object : retrofit2.Callback<CheckNickData> {
            override fun onResponse(
                call: Call<CheckNickData>,
                response: Response<CheckNickData>
            ) {
                flag = response.body()!!.data
                if (flag) {         //닉네임 사용할 수 있으면
                    binding.nickCheck.visibility = View.INVISIBLE
                    binding.nickCheckText.visibility = View.INVISIBLE
                } else {
                    binding.nickCheckText.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<CheckNickData>, t: Throwable) {
                Log.d("실패", ";ㅁ;")
            }
        })
    }
}