package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junjange.myapplication.databinding.ActivityAppInformationBinding

class AppInformationActivity : AppCompatActivity() {
    lateinit var binding: ActivityAppInformationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAppInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}