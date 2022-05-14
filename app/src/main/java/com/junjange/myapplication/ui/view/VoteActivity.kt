package com.junjange.myapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.junjange.myapplication.adapter.CommentsAdapter
import com.junjange.myapplication.adapter.NormalVoteAdapter
import com.junjange.myapplication.adapter.PhotoVoteAdapter
import com.junjange.myapplication.databinding.ActivityVoteBinding
import com.junjange.myapplication.ui.viewmodel.VoteViewModel

class VoteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityVoteBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, VoteViewModel.Factory(application))[VoteViewModel::class.java] }
    private lateinit var photoVoteAdapter: PhotoVoteAdapter
    private lateinit var normalVoteAdapter: NormalVoteAdapter
    private lateinit var commentsAdapter: CommentsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // 입력에 따라 일반투표/사진투표 리사이클러뷰 실행
        normalSetView()
        normalSetObserver()
        commentSetView()
        commentSetObserver()

//        photoSetView()
//        photoSetObserver()
    }


    private fun normalSetView(){
        normalVoteAdapter =  NormalVoteAdapter().apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.normalVoteList.visibility = View.VISIBLE
        binding.photoVoteList.visibility = View.GONE
        binding.normalVoteList.adapter = normalVoteAdapter
    }

    private fun normalSetObserver() {
        viewModel.retrofitTodoList.observe(this, {

            viewModel.retrofitTodoList.value?.let { it1 -> normalVoteAdapter.setData(it1) }
        })
    }

    private fun photoSetView(){
        photoVoteAdapter =  PhotoVoteAdapter().apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.normalVoteList.visibility = View.GONE
        binding.photoVoteList.visibility = View.VISIBLE
        binding.photoVoteList.adapter = photoVoteAdapter
    }

    private fun photoSetObserver() {
        viewModel.retrofitTodoList.observe(this, {

            viewModel.retrofitTodoList.value?.let { it1 -> photoVoteAdapter.setData(it1) }
        })
    }

    private fun commentSetView(){
        commentsAdapter =  CommentsAdapter().apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.commentList.adapter = commentsAdapter

    }

    private fun commentSetObserver() {
        viewModel.retrofitCommentList.observe(this, {

            viewModel.retrofitCommentList.value?.let { it1 -> commentsAdapter.setData(it1) }
        })
    }
}