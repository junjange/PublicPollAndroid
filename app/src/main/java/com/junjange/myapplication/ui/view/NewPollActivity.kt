package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.junjange.myapplication.databinding.ActivityNewPollBinding

class NewPollActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewPollBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewPollBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}