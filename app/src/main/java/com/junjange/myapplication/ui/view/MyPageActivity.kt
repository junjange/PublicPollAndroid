package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.junjange.myapplication.databinding.ActivityMyPageBinding

class MyPageActivity : AppCompatActivity() {
    lateinit var binding: ActivityMyPageBinding
    private var idToken: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        idToken = intent.getStringExtra("Token")

        Log.d("여긴,,?", idToken.toString())

        binding.editInformationBt.setOnClickListener{
            val intent = Intent(this, EditInformationActivity::class.java)
            intent.putExtra("Token", idToken)
            startActivity(intent)
        }

        binding.appInformationBt.setOnClickListener{
            val intent = Intent(this, AppInformationActivity::class.java)
            startActivity(intent)
        }

        binding.contactUsBt.setOnClickListener {
            val intent = Intent(this, ContactUsActivity::class.java)
            startActivity(intent)
        }
    }
}