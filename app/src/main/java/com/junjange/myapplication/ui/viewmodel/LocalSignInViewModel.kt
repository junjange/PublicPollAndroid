package com.junjange.myapplication.ui.viewmodel

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.junjange.myapplication.network.PollsObject
import com.junjange.myapplication.ui.view.HomeActivity

class LocalSignInViewModel() : ViewModel() {
    private lateinit var auth: FirebaseAuth

    fun SignIn(email : String, password: String, activity: Activity) {
        auth = Firebase.auth

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val intent = Intent(activity, HomeActivity::class.java)
                    user?.getIdToken(true)
                        ?.addOnCompleteListener(OnCompleteListener<GetTokenResult>() { task ->
                            if (task.isSuccessful()) {
                                val idToken = task.getResult().getToken().toString();
                                Log.d("토큰", idToken)
                                intent.putExtra("Token", idToken)
                                activity.startActivity(intent)
                                PollsObject.token = idToken // 토큰 사용!

                            }

                        });

                } else {
                    Toast.makeText(activity, "로그인 실패", Toast.LENGTH_SHORT).show()
                }
            }
    }
}