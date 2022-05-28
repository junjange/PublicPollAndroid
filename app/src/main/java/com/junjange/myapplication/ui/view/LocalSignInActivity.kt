package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.junjange.myapplication.databinding.ActivityLocalSignInBinding
import com.junjange.myapplication.ui.viewmodel.LocalSignInViewModel

class LocalSignInActivity : AppCompatActivity() {
    lateinit var binding: ActivityLocalSignInBinding
    private val viewModel : LocalSignInViewModel by viewModels()


    var email : String = ""
    var password : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalSignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.emailStr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                email = binding.emailStr.text.toString()
            }
        })

        binding.passwordStr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                password = binding.passwordStr.text.toString()
            }
        })

        binding.nextBt.setOnClickListener {
            viewModel.SignIn(email, password, this)
        }
    }
}