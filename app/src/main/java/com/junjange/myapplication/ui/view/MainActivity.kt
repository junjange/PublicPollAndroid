package com.junjange.myapplication.ui.view

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil.setContentView
import com.junjange.myapplication.R
import com.junjange.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setContentView(this, R.layout.activity_main)
        binding.mainActivity = this


        // 빠른 투표 1번 항목 클릭시
        binding.quickQuestion1Bg.setOnClickListener {
            binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
            binding.quickQuestion1Bg.strokeColor = Color.parseColor("#abbced")
            binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
            binding.quickQuestion2Bg.strokeColor = Color.parseColor("#b3b6e8")

            binding.quickQuestion1Txt.setTextColor(Color.BLACK)
            binding.quickQuestion2Txt.setTextColor(Color.parseColor("#989898"))

            binding.quickQuestion1Turnout.setTextColor(Color.BLACK)
            binding.quickQuestion2Turnout.setTextColor(Color.parseColor("#989898"))
            binding.quickQuestion1Turnout.visibility = View.VISIBLE
            binding.quickQuestion2Turnout.visibility = View.VISIBLE


        }

        // 빠른 투표 2번 항목 클릭시
        binding.quickQuestion2Bg.setOnClickListener {
            binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
            binding.quickQuestion1Bg.strokeColor = Color.parseColor("#b3b6e8")
            binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
            binding.quickQuestion2Bg.strokeColor = Color.parseColor("#abbced")

            binding.quickQuestion1Txt.setTextColor(Color.parseColor("#989898"))
            binding.quickQuestion2Txt.setTextColor(Color.BLACK)

            binding.quickQuestion1Turnout.setTextColor(Color.parseColor("#989898"))
            binding.quickQuestion2Turnout.setTextColor(Color.BLACK)
            binding.quickQuestion1Turnout.visibility = View.VISIBLE
            binding.quickQuestion2Turnout.visibility = View.VISIBLE

        }


        binding.searchBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))

        }

        binding.hotPollsBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, HotPollsActivity::class.java))

        }

        binding.allPollsBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, PollsActivity::class.java))

        }

    }
}