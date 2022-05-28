package com.junjange.myapplication.ui.viewmodel

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.junjange.myapplication.ui.view.SignUpSecondActivity

class LocalSignUpViewModel() : ViewModel(){
    private lateinit var auth: FirebaseAuth

    fun signUpFirebase(email : String, password : String, activity : Activity) {
        auth = FirebaseAuth.getInstance()

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    val intent = Intent(activity, SignUpSecondActivity::class.java)
                    intent.putExtra("email", email)

                    user?.getIdToken(true)
                        ?.addOnCompleteListener(OnCompleteListener<GetTokenResult>() {
                                task ->
                            if (task.isSuccessful()) {
                                val idToken = task.getResult().getToken().toString();
                                intent.putExtra("Token", idToken)
                                activity.startActivity(intent)
                            }

                        });

                } else {
                    Log.w(ContentValues.TAG, "createUserWithEmail:failure", task.exception)
                }
            }
    }
}