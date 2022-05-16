package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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

        // 투표 검색 페이지로 이동
        binding.searchBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, SearchActivity::class.java))

        }

        // 핫 투표 페이지로 이동
        binding.hotPollsBtn.setOnClickListener {
            startActivity( Intent(this@MainActivity, HotPollsActivity::class.java))

        }

        // 모든 투표 페이지로 이동
        binding.allPollsBtn.setOnClickListener {
            startActivity(Intent(this@MainActivity, PollsActivity::class.java))

        }

        // 투표 생성 페이지로 이동
        binding.newPollBtn.setOnClickListener {
            Toast.makeText(this, "투표 생성 페이지로 이동", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(this@MainActivity, ::class.java))

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