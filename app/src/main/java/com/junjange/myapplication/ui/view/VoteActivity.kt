package com.junjange.myapplication.ui.view

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.CommentsAdapter
import com.junjange.myapplication.adapter.NormalVoteAdapter
import com.junjange.myapplication.adapter.PhotoVoteAdapter
import com.junjange.myapplication.data.ModelBoardComponent
import com.junjange.myapplication.databinding.ActivityVoteBinding
import com.junjange.myapplication.ui.viewmodel.VoteViewModel


class VoteActivity : AppCompatActivity(), NormalVoteAdapter.ItemClickListener, PhotoVoteAdapter.ItemClickListener {

    private val binding by lazy { ActivityVoteBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, VoteViewModel.Factory(application))[VoteViewModel::class.java] }
    private lateinit var photoVoteAdapter: PhotoVoteAdapter
    private lateinit var normalVoteAdapter: NormalVoteAdapter
    private lateinit var commentsAdapter: CommentsAdapter

    private var photoCheckBox = -1
    private var normalCheckBox = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 키보드 설정
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // 입력에 따라 일반투표/사진투표 리사이클러뷰 실행
        normalSetView()
        normalSetObserver()
//        photoSetView()
//        photoSetObserver()

        // 투표 유무에 따라 댓글 리사이클러뷰 실행
        commentSetView()
        commentSetObserver()

        // 검색창 엔터
        binding.etCommentEnter.setOnClickListener {

            binding.etCommentField.clearFocus()
            imm.hideSoftInputFromWindow(binding.etCommentField.windowToken, 0)
            binding.etCommentField.text.clear()
        }

    }


    private fun normalSetView(){
        normalVoteAdapter =  NormalVoteAdapter(this).apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.normalVoteList.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
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
        photoVoteAdapter =  PhotoVoteAdapter(this).apply {
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


    /***
     * 투표를 한 후에 background, text 색이 변해야한다.
     * Activity에서 색 변화를 진행할 경우 background만 변화 가능
     * Adapter에서 색 변화를 진행할 경우 background, text 색 모두 변화시 킬 수 있지만 다른 item의 색을 변화시킬 수 없어 원래 상태로 바꿀 수 없음.
     * 어떤식으로 해결해야할지 고민이 필요함..
     */
    @SuppressLint("WrongConstant")
    override fun onNormalVoteClickListener(item: ModelBoardComponent, position: Int) {

        if (normalCheckBox != position) {
            binding.normalVoteList.children.iterator().forEach { item ->

                item.setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            }

            normalCheckBox = position
            binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)
            binding.normalVoteList[position].context.getColor(R.color.white)


        } else {

            binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            normalCheckBox = -1
        }
    }

    override fun onPhotoVoteClickListener(item: ModelBoardComponent, position: Int) {
        if (normalCheckBox != position) {
            binding.photoVoteList.children.iterator().forEach { item ->

                item.setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            }

            normalCheckBox = position
            binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)
            binding.photoVoteList[position].context.getColor(R.color.white)


        } else {

            binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            normalCheckBox = -1
        }
    }


}