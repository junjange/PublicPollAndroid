package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.HotPollsAdapter
import com.junjange.myapplication.adapter.PollsAdapter
import com.junjange.myapplication.databinding.ActivityHotPollsBinding
import com.junjange.myapplication.databinding.ActivityPollsBinding
import com.junjange.myapplication.repository.HotPollsRepository
import com.junjange.myapplication.ui.viewmodel.HotPollsViewModel
import com.junjange.myapplication.ui.viewmodel.PollsViewModel

class HotPollsActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHotPollsBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, HotPollsViewModel.Factory(application))[HotPollsViewModel::class.java] }
    private lateinit var retrofitAdapter: HotPollsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setView()
        setObserver()


    }

    private fun setView(){
        retrofitAdapter =  HotPollsAdapter().apply {
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