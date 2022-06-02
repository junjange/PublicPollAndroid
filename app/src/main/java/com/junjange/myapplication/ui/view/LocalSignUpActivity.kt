package com.junjange.myapplication.ui.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.junjange.myapplication.databinding.ActivityLocalSignUpBinding
import com.junjange.myapplication.ui.viewmodel.LocalSignUpViewModel

class LocalSignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivityLocalSignUpBinding
    private val viewModel : LocalSignUpViewModel by viewModels()
    private lateinit var auth: FirebaseAuth
    private var email : String = ""
    private var password : String = ""
    private var passwordCheck : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocalSignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setClear()

        auth = FirebaseAuth.getInstance()

        binding.nextBt.setOnClickListener {
            if (password != passwordCheck) {
                Toast.makeText(this, "Check PassWord Correct Please" , Toast.LENGTH_SHORT).show()
            }else {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    viewModel.signUpFirebase(binding.emailStr.text.toString(), binding.passwordStr.text.toString(), this)
                }
            }
        }

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

        binding.checkPasswordStr.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                passwordCheck = binding.checkPasswordStr.text.toString()

                if (password != passwordCheck) {
                    binding.passwordCorrectText.visibility = View.VISIBLE
                } else {
                    binding.passwordCorrectText.visibility = View.INVISIBLE
                }
            }
        })

    }

    fun setClear() {
        binding.emailStr.text.clear()
        binding.passwordStr.text.clear()
        binding.checkPasswordStr.text.clear()
    }
}