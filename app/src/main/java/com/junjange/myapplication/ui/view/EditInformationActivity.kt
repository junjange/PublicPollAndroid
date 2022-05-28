package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.viewModels
import com.junjange.myapplication.databinding.ActivityEditInformationBinding
import com.junjange.myapplication.ui.viewmodel.EditInformationViewModel

class EditInformationActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditInformationBinding
    private val viewModel : EditInformationViewModel by viewModels()
    private var isChecking = true
    private var mCheckedId = 0
    private var email = ""
    private var nickname = ""
    private var gender = ""
    private var age = 0
    private var tier = 1
    private var idToken: String? = null
    private var user_interest = arrayOf<String>("","","")
    private var tagCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idToken = intent.getStringExtra("Token")

        viewModel.myPageSetting(idToken!!, binding)

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

        binding.nicknameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                nickname = binding.nicknameEdit.text.toString()
            }
        })

        binding.gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.radioMan.id) {
                gender = "m"
            } else if (checkedId == binding.radioWoman.id) {
                gender = "w"
            }
        }

        binding.gender.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == binding.radioMan.id) {
                gender = "m"
            } else if (checkedId == binding.radioWoman.id) {
                gender = "w"
            }
        }

        binding.doneBt.setOnClickListener {
            email = binding.emailEdit.text.toString()
            viewModel.editMyPage(idToken!!, email, nickname, age, gender, user_interest, binding)
        }

    }


}