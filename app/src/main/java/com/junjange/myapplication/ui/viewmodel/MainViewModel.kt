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
import com.junjange.myapplication.ui.view.SignUpSecondActivity

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
                    val intent = Intent(activity, SignUpSecondActivity::class.java)
                    user?.getIdToken(true)
                        ?.addOnCompleteListener(OnCompleteListener<GetTokenResult>() { task ->
                            if (task.isSuccessful()) {
                                val idToken = task.getResult().getToken().toString();
                                intent.putExtra("Token", idToken)
                                intent.putExtra("email", user.email.toString())
                                activity.startActivity(intent)
                            }
                        });
                } else {
                    Log.d("실패", "signInWithgoogle: failure", task.exception)
                }
            }
    }
}