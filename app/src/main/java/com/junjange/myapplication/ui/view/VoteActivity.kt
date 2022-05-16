package com.junjange.myapplication.ui.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.ColorStateListDrawable
import android.graphics.drawable.RotateDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.card.MaterialCardView
import com.google.android.material.color.ColorRoles
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.CommentsAdapter
import com.junjange.myapplication.adapter.ItemAdapter
import com.junjange.myapplication.adapter.NormalVoteAdapter
import com.junjange.myapplication.adapter.PhotoVoteAdapter
import com.junjange.myapplication.data.Item
import com.junjange.myapplication.data.ModelBoardComponent
import com.junjange.myapplication.databinding.ActivityVoteBinding
import com.junjange.myapplication.ui.viewmodel.VoteViewModel

class VoteActivity : AppCompatActivity(), NormalVoteAdapter.ItemClickListener1 {

    private val binding by lazy { ActivityVoteBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, VoteViewModel.Factory(application))[VoteViewModel::class.java] }
    private lateinit var photoVoteAdapter: PhotoVoteAdapter
    private lateinit var normalVoteAdapter: NormalVoteAdapter
    private lateinit var commentsAdapter: CommentsAdapter

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



    override fun onItemClickListener1(item: ModelBoardComponent, position: Int) {
        Log.d("ttt", (binding.normalVoteList[position]).toString())

        Log.d("ttt", position.toString())

//        binding.quickQuestion1Bg.setCardBackgroundColor(Color.parseColor("#e9efff"))
//        binding.quickQuestion1Bg.strokeColor = Color.parseColor("#abbced")
//        binding.quickQuestion2Bg.setCardBackgroundColor(Color.parseColor("#e9ebff"))
//        binding.quickQuestion2Bg.strokeColor = Color.parseColor("#b3b6e8")
//        binding.quickQuestion1Txt.setTextColor(Color.BLACK)
//        binding.quickQuestion2Txt.setTextColor(Color.parseColor("#989898"))
//        binding.quickQuestion1Turnout.setTextColor(Color.BLACK)
//        binding.quickQuestion2Turnout.setTextColor(Color.parseColor("#989898"))
//        binding.quickQuestion1Turnout.visibility = View.VISIBLE
//        binding.quickQuestion2Turnout.visibility = View.VISIBLE

        binding.normalVoteList[position].setBackgroundColor(resources.getColor(R.color.white))
        val itemBackground: ColorDrawable =
            binding.normalVoteList[position].background as ColorDrawable
        if (itemBackground.color == ContextCompat.getColor(this, R.color.white)) {
            binding.normalVoteList.children.iterator().forEach { item ->

                item.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
            binding.normalVoteList[position].setBackgroundColor(
                ContextCompat.getColor(this, R.color.teal_200)
            )

//            binding.normalVoteList[position].
        } else {
            binding.normalVoteList.children.iterator().forEach { item ->
                item.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.white
                    )
                )
            }
        }
    }


}