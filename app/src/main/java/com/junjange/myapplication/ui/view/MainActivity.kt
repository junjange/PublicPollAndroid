package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.junjange.myapplication.adapter.QuickVoteAdapter
import com.junjange.myapplication.databinding.ActivityMainBinding
import com.junjange.myapplication.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, MainViewModel.Factory(application))[MainViewModel::class.java] }
    private lateinit var retrofitAdapter: QuickVoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setView()
        setObserver()

//
//        // 빠른 투표 1번 항목 클릭시
//        binding.quickQuestion1Bg.setOnClickListener {
//            binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
//            binding.quickQuestion1Bg.strokeColor = Color.parseColor("#abbced")
//            binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
//            binding.quickQuestion2Bg.strokeColor = Color.parseColor("#b3b6e8")
//
//            binding.quickQuestion1Txt.setTextColor(Color.BLACK)
//            binding.quickQuestion2Txt.setTextColor(Color.parseColor("#989898"))
//
//            binding.quickQuestion1Turnout.setTextColor(Color.BLACK)
//            binding.quickQuestion2Turnout.setTextColor(Color.parseColor("#989898"))
//            binding.quickQuestion1Turnout.visibility = View.VISIBLE
//            binding.quickQuestion2Turnout.visibility = View.VISIBLE
//
//
//        }
//
//        // 빠른 투표 2번 항목 클릭시
//        binding.quickQuestion2Bg.setOnClickListener {
//            binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
//            binding.quickQuestion1Bg.strokeColor = Color.parseColor("#b3b6e8")
//            binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
//            binding.quickQuestion2Bg.strokeColor = Color.parseColor("#abbced")
//
//            binding.quickQuestion1Txt.setTextColor(Color.parseColor("#989898"))
//            binding.quickQuestion2Txt.setTextColor(Color.BLACK)
//
//            binding.quickQuestion1Turnout.setTextColor(Color.parseColor("#989898"))
//            binding.quickQuestion2Turnout.setTextColor(Color.BLACK)
//            binding.quickQuestion1Turnout.visibility = View.VISIBLE
//            binding.quickQuestion2Turnout.visibility = View.VISIBLE
//
//        }


        binding.searchBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))

        }

        binding.hotPollsBtn.setOnClickListener {
            startActivity( Intent(this@MainActivity, HotPollsActivity::class.java))

        }

        binding.allPollsBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, PollsActivity::class.java))

        }

        binding.newPollBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, VoteActivity::class.java))


        }

    }

    private fun setView(){
        retrofitAdapter =  QuickVoteAdapter().apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.rvList.adapter = retrofitAdapter
    }

    private fun setObserver() {
        viewModel.retrofitTodoList.observe(this, {

            viewModel.retrofitTodoList.value?.let { it1 -> retrofitAdapter.setData(it1) }
        })

    }
}