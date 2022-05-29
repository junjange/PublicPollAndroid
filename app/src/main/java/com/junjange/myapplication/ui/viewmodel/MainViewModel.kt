package com.junjange.myapplication.ui.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.junjange.myapplication.data.MyPage
import com.junjange.myapplication.network.PollsObject
import com.junjange.myapplication.ui.view.HomeActivity
import com.junjange.myapplication.ui.view.SignUpSecondActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel() : ViewModel()  {
    private lateinit var auth: FirebaseAuth

    fun firebaseAuthWithGoogle(idToken: String, activity: Activity) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth = Firebase.auth

        auth.signInWithCredential(credential)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d("성공", user.toString())
                    user?.getIdToken(true)
                        ?.addOnCompleteListener(OnCompleteListener<GetTokenResult>() { task ->
                            if (task.isSuccessful()) {
                                val idTokenfirebase = task.getResult().getToken().toString();
                                PollsObject.token = idTokenfirebase // 토큰 사용!
                                isPossibleSignIn(user.email.toString(), activity)
                            }
                        });
                } else {
                    Log.d("실패", "signInWithgoogle: failure", task.exception)
                }
            }
    }

    fun isPossibleSignIn(email : String, activity: Activity) {
        val service = PollsObject.getRetrofitService
        val call = service.postSignIn()
        call.enqueue(object : Callback<MyPage> {
            override fun onResponse(
                call: Call<MyPage>,
                response: Response<MyPage>
            ) {
                Log.d("성공", response.body().toString())

                if (response.body()!!.statusCode.toInt() == 400) {
                    val intent = Intent(activity, SignUpSecondActivity::class.java)
                    intent.putExtra("email", email)
                    activity.startActivity(intent)
                } else {
                    activity.startActivity(Intent(activity, HomeActivity::class.java))
                }
            }

            override fun onFailure(call: Call<MyPage>, t: Throwable) {
                Log.d("실패", ";ㅁ;")
            }
        })
    }
}