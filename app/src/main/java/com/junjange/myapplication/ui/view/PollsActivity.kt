package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.PollsAdapter
import com.junjange.myapplication.adapter.QuickVoteAdapter
import com.junjange.myapplication.databinding.ActivityMainBinding
import com.junjange.myapplication.databinding.ActivityPollsBinding
import com.junjange.myapplication.ui.viewmodel.MainViewModel
import com.junjange.myapplication.ui.viewmodel.PollsViewModel

class PollsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityPollsBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, PollsViewModel.Factory(application))[PollsViewModel::class.java] }
    private lateinit var retrofitAdapter: PollsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setView()
        setObserver()

    }

    private fun setView(){
        retrofitAdapter =  PollsAdapter().apply {
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