package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.view.GravityCompat
import androidx.core.view.children
import androidx.core.view.get
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.CommentsAdapter
import com.junjange.myapplication.adapter.NormalVoteAdapter
import com.junjange.myapplication.adapter.PhotoVoteAdapter
import com.junjange.myapplication.data.ItemComponent
import com.junjange.myapplication.databinding.ActivityVoteBinding
import com.junjange.myapplication.ui.viewmodel.VoteViewModel


class VoteActivity : AppCompatActivity(), NormalVoteAdapter.ItemClickListener, PhotoVoteAdapter.ItemClickListener,
    NavigationView.OnNavigationItemSelectedListener {

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


        /**
         * drawer
         *
         * */
        setSupportActionBar(binding.mainToolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze2_24) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        binding.mainNavigationView.setNavigationItemSelectedListener(this) //navigation 리스너

        // 키보드 설정
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager

        // 데이터 바인딩
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        val id = intent.getSerializableExtra("id") as Int
        val presentImagePath = intent.getSerializableExtra("presentImagePath")


        viewModel.getViewPollsRetrofit(id)
        viewModel.getCommentsRetrofit(id)
        commentSetView()
        commentSetObserver()

        // 사진유무에 따라 일반투표/사진투표 리사이클러뷰 실행
        if (presentImagePath == null){
            normalSetView()
            normalSetObserver()

        }else{
            photoSetView()
            photoSetObserver()

        }





        // 검색창 엔터
        binding.etCommentEnter.setOnClickListener {
            viewModel.postCommentRetrofit(id, binding.etCommentField.text.toString())

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

        viewModel.retrofitViewPolls.observe(this, {

            viewModel.retrofitViewPolls.value?.let { it1 -> normalVoteAdapter.setData(it1) }
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
        viewModel.retrofitViewPolls.observe(this, {

            viewModel.retrofitViewPolls.value?.let { it1 -> photoVoteAdapter.setData(it1) }
        })
    }

    private fun commentSetView(){

        commentsAdapter =  CommentsAdapter().apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.commentList.adapter = commentsAdapter



    }

    private fun commentSetObserver() {
        viewModel.retrofitViewPolls.observe(this, {

            viewModel.retrofitCommentList.value?.let { it1 -> commentsAdapter.setData(it1) }

            val commentCnt = commentsAdapter.itemCount
            binding.commentCnt.text = commentCnt.toString()
//            if (commentCnt == 0){
//                binding.commentList.visibility = View.GONE
//                binding.noCommentTxt.visibility = View.VISIBLE
//            }else{
//                binding.commentList.visibility = View.VISIBLE
//                binding.noCommentTxt.visibility = View.GONE
//            }
        })
    }


    /***
     * 투표를 한 후에 background, text 색이 변해야한다.
     * Activity에서 색 변화를 진행할 경우 background만 변화 가능
     * Adapter에서 색 변화를 진행할 경우 background, text 색 모두 변화시 킬 수 있지만 다른 item의 색을 변화시킬 수 없어 원래 상태로 바꿀 수 없음.
     * 어떤식으로 해결해야할지 고민이 필요함..
     */
    override fun onNormalVoteClickListener(item: ItemComponent, position: Int) {

        if (normalCheckBox != position) {
            binding.normalVoteList.children.iterator().forEach { item ->

                item.setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            }

            normalCheckBox = position
            binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)
            binding.normalVoteList[position].context.getColor(R.color.white)
            Log.d("Ttt", item.toString())



        } else {

            binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            normalCheckBox = -1
        }
    }

    override fun onPhotoVoteClickListener(item: ItemComponent, position: Int) {
        if (normalCheckBox != position) {
            binding.photoVoteList.children.iterator().forEach { item ->

                item.setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            }

            normalCheckBox = position
            binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)


        } else {

            binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
            normalCheckBox = -1
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{ // 메뉴 버튼
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.mainDrawerLayout.closeDrawers()
        when(item.itemId){
            R.id.mainPageDrawer-> {
                startActivity( Intent(this@VoteActivity, MainActivity::class.java))

            }
            R.id.allPollsDrawer-> {
                startActivity( Intent(this@VoteActivity, PollsActivity::class.java))

            }
            R.id.hotPollsDrawer-> {
                startActivity( Intent(this@VoteActivity, HotPollsActivity::class.java))

            }
            R.id.searchDrawer-> {
                startActivity( Intent(this@VoteActivity, SearchActivity::class.java))

            }
            R.id.myPageDrawer-> {
                // My Page 이동
//                startActivity( Intent(this@VoteActivity, HotPollsActivity::class.java))

            }

        }
        return false
    }

    override fun onBackPressed() { //뒤로가기 처리
        if(binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawerLayout.closeDrawers()

        } else{
            super.onBackPressed()
        }
    }


}