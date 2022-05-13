package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.junjange.myapplication.adapter.PhotoVoteAdapter
import com.junjange.myapplication.databinding.ActivityVoteBinding
import com.junjange.myapplication.ui.viewmodel.VoteViewModel

class VoteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityVoteBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, VoteViewModel.Factory(application))[VoteViewModel::class.java] }
    private lateinit var retrofitAdapter: PhotoVoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        setView()
        setObserver()
    }


    private fun setView(){
        retrofitAdapter =  PhotoVoteAdapter().apply {
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