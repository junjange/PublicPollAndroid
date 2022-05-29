package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.viewModels
import com.junjange.myapplication.databinding.ActivitySignUpSecondBinding
import com.junjange.myapplication.ui.viewmodel.SignUpSecondViewModel

class SignUpSecondActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpSecondBinding
    private val viewModel : SignUpSecondViewModel by viewModels()
    private var isChecking = true
    private var mCheckedId = 0
    private var email = ""
    private var nickname = ""
    private var gender = ""
    private var age = 0
    private var tier = 1
    private var user_interest = arrayOf<String>("","","")
    private var tagCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpSecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = intent.getStringExtra("email").toString()

        binding.nicknameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                nickname = binding.nicknameEdit.text.toString()
            }
        })

        binding.ageFirstLine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.ageSecondLine.clearCheck()
                mCheckedId = checkedId
            }
            if (mCheckedId == binding.age10s.id) {
                age = 10
            } else if (mCheckedId == binding.age20s.id) {
                age = 20
            } else if (mCheckedId == binding.age30s.id) {
                age = 30
            }
            isChecking = true
        }
        binding.ageSecondLine.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                binding.ageFirstLine.clearCheck()
                mCheckedId = checkedId
            }

            if (mCheckedId == binding.age40s.id) {
                age = 40
            } else if (mCheckedId == binding.age50s.id) {
                age = 50
            }

            isChecking = true
        }

        binding.gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.man.id) {
                gender = "m"
            } else if (checkedId == binding.woman.id) {
                gender = "w"
            }
        }

        binding.chip1.setOnClickListener {
            user_interest[tagCount++] = "chip1"
        }
        binding.chip2.setOnClickListener {
            user_interest[tagCount++] = "chip2"
        }
        binding.chip3.setOnClickListener {
            user_interest[tagCount++] = "chip3"
        }
        binding.chip4.setOnClickListener {
            user_interest[tagCount++] = "chip4"
        }
        binding.chip5.setOnClickListener {
            user_interest[tagCount++] = "chip5"
        }
        binding.chip6.setOnClickListener {
            user_interest[tagCount++] = "chip6"
        }
        binding.chip7.setOnClickListener {
            user_interest[tagCount++] = "chip7"
        }
        binding.chip8.setOnClickListener {
            user_interest[tagCount++] = "chip8"
        }
        binding.chip9.setOnClickListener {
            user_interest[tagCount++] = "chip9"
        }

        binding.joinBt.setOnClickListener {
            viewModel.signUp(email, nickname, age, gender, user_interest, this)

            val intent = Intent(this, LocalSignInActivity::class.java)
            startActivity(intent)
        }

    }
//    fun setChipEnable(status: Boolean) {
//        for (i in 0 until binding.tagChip.getChildCount()) {
//            binding.tagChip.getChildAt(i).isEnabled = status
//        }
//    }
}