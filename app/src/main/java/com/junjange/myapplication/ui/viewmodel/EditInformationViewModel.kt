package com.junjange.myapplication.ui.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.ViewModel
import com.junjange.myapplication.data.MyPage
import com.junjange.myapplication.data.MyPageEdit
import com.junjange.myapplication.data.SignUpReponse
import com.junjange.myapplication.databinding.ActivityEditInformationBinding
import com.junjange.myapplication.network.PollsObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditInformationViewModel() : ViewModel() {
    var email : String = ""
    var nickname : String = ""
    var gender : String = ""
    var age : Int? = null

    fun myPageSetting(idToken : String, binding : ActivityEditInformationBinding) {
        val service = PollsObject.getRetrofitService
        val call = service.getMyPageGet(idToken.toString())
        call.enqueue(object : Callback<MyPage> {
            override fun onResponse(
                call: Call<MyPage>,
                response: Response<MyPage>
            ) {
                Log.d("성공", response.body().toString())
                email = response.body()!!.data.email
                binding.emailEdit.setText(email)
                nickname = response.body()!!.data.nick
                binding.nicknameEdit.setText(nickname)
                gender = response.body()!!.data.gender
                genderSet(gender, binding)
                age = response.body()!!.data.age
                ageSet(age, binding)
            }

            override fun onFailure(call: Call<MyPage>, t: Throwable) {
                Log.d("실패", ";ㅁ;")
            }
        })
    }

    fun editMyPage(idToken: String, email : String, nickname : String, age : Int, gender : String, userInterest : Array<String>, binding: ActivityEditInformationBinding) {
        val service = PollsObject.getRetrofitService
        val call = service.postMyPageEdit(idToken.toString(), MyPageEdit(email, nickname, age, gender, userInterest[0],userInterest[1],userInterest[2]))

        call.enqueue(object : Callback<SignUpReponse> {
            override fun onResponse(
                call: Call<SignUpReponse>,
                response: Response<SignUpReponse>
            ) {
                Log.d("성공", response.body().toString())

                myPageSetting(idToken, binding)
            }

            override fun onFailure(call: Call<SignUpReponse>, t: Throwable) {
                Log.d("실패", "크크루삥뽕")
            }
        })
    }
    private fun genderSet(gender: String, binding : ActivityEditInformationBinding) {
        if (gender.equals("m")) {
            binding.radioMan.isChecked = true
        } else if (gender.equals("w")){
            binding.radioWoman.isChecked = true
        }
    }

    private fun ageSet(age: Int?, binding : ActivityEditInformationBinding) {
        if (age == 10) {
            binding.age10s.isChecked = true
        } else if (age == 20) {
            binding.age20s.isChecked = true
        } else if (age == 30) {
            binding.age30s.isChecked = true
        } else if (age == 40) {
            binding.age40s.isChecked = true
        } else if (age == 50) {
            binding.age50s.isChecked = true
        }
    }
}