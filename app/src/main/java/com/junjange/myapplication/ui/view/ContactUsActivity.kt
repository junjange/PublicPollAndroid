package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junjange.myapplication.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {
    lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}