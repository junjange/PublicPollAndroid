package com.junjange.myapplication.ui.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.junjange.myapplication.databinding.ActivityLocalSignUpBinding
import com.junjange.myapplication.ui.viewmodel.LocalSignUpViewModel

class LocalSignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityLocalSignUpBinding
    private val viewModel : LocalSignUpViewModel by viewModels()
    private lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.nextBt.setOnClickListener {
            if (binding.emailStr.text.toString().isNotEmpty() && binding.passwordStr.text.toString().isNotEmpty()) {
                viewModel.signUpFirebase(binding.emailStr.text.toString(), binding.passwordStr.text.toString(), this)

            }

        }

    }
}