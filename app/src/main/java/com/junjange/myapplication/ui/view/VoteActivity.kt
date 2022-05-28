package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
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
import com.junjange.myapplication.data.StatsItem
import com.junjange.myapplication.databinding.ActivityVoteBinding
import com.junjange.myapplication.ui.viewmodel.VoteViewModel


class VoteActivity : AppCompatActivity(), NormalVoteAdapter.ItemClickListener, PhotoVoteAdapter.ItemClickListener,
    NavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy { ActivityVoteBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, VoteViewModel.Factory(application))[VoteViewModel::class.java] }
    private lateinit var photoVoteAdapter: PhotoVoteAdapter
    private lateinit var normalVoteAdapter: NormalVoteAdapter
    private lateinit var commentsAdapter: CommentsAdapter
    private var checkVote = arrayListOf<Int>()
    private var canReVote = false
    private var canComment = true
    private var myBallots : ArrayList<Int>? = null
    private var stats : ArrayList<StatsItem>? = null
    private var voteState: Boolean = false



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
        voteState = intent.getBooleanExtra("voteState", false)

        viewModel.getCommentsRetrofit(id)
        viewModel.getViewPollsRetrofit(id)

        commentSetView()
        commentSetObserver()

        // 사진유무에 따라 일반투표/사진투표 리사이클러뷰 실행
        if (presentImagePath == null){
            normalSetView(voteState)
            normalSetObserver()

        }else{
            photoSetView()
            photoSetObserver()

        }

        binding.voteBtn.setOnClickListener {
            if(checkVote.size > 0){

                if (voteState){
                    viewModel.postReVoteRetrofit(id, checkVote)

                }else{
                      viewModel.postBallotRetrofit(id, checkVote)

                }
                Log.d("ttt1", checkVote.toString())

                // 원하는 화면 연결
                val intent: Intent =  Intent(this@VoteActivity, VoteActivity::class.java).apply {
                    // 데이터 전달
                    putExtra("id", id)
                    putExtra("presentImagePath", presentImagePath)
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                }
                this.startActivity(intent)
            }else{
                Toast.makeText(this,"항목을 선택해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

        binding.reVoteBtn.setOnClickListener {

            // 원하는 화면 연결
            val intent: Intent = Intent(this@VoteActivity, VoteActivity::class.java).apply {
                // 데이터 전달
                putExtra("id", id)
                putExtra("presentImagePath", presentImagePath)
                putExtra("voteState", true)
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

            }
            this.startActivity(intent)
        }

        // 검색창 엔터
        binding.etCommentEnter.setOnKeyListener { _, keyCode, event ->

            if ((event.action== KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // 엔터가 눌릴 때 하고 싶은 일

                viewModel.postCommentRetrofit(id, binding.etCommentField.text.toString())
                binding.etCommentField.clearFocus()
                binding.etCommentField.text.clear()
                imm.hideSoftInputFromWindow(binding.etCommentField.windowToken, 0)
                true

            } else {

                false

            }
        }

        // 검색창 엔터
        binding.etCommentEnter.setOnClickListener {
            viewModel.postCommentRetrofit(id, binding.etCommentField.text.toString())
            binding.etCommentField.clearFocus()
            binding.etCommentField.text.clear()
            imm.hideSoftInputFromWindow(binding.etCommentField.windowToken, 0)
        }

    }


    private fun normalSetView(voteState : Boolean){

        normalVoteAdapter =  NormalVoteAdapter(this, voteState).apply {
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

            viewModel.retrofitViewPolls.value?.let { it1 -> normalVoteAdapter.setData(it1)
                binding.title.text = it1.viewPollsItem.contents
                canReVote = it1.viewPollsItem.canRevote
                canComment = it1.viewPollsItem.canComment
                // 익명
                if(it1.viewPollsItem.showNick) binding.nick.text = it1.viewPollsItem.nick else binding.nick.text = "anonymous"


                // 투표유무
                if (it1.viewPollsItem.myBallots == null || voteState) {
                    binding.voteBtn.visibility = View.VISIBLE
                    binding.reVoteBtn.visibility = View.GONE
                    binding.statisticsBtn.visibility = View.GONE
                    binding.commentList.visibility = View.GONE
                    binding.commentCnt.visibility = View.GONE
                    binding.commentIcon.visibility = View.GONE

                } else{

                    binding.voteBtn.visibility = View.GONE
                    binding.reVoteBtn.visibility = View.VISIBLE
                    binding.statisticsBtn.visibility = View.VISIBLE

                    // 댓글
                    if (it1.viewPollsItem.canComment){
                        if(it1.viewPollsItem.canRevote) binding.statisticsBtn.visibility = View.VISIBLE


                        binding.commentList.visibility = View.VISIBLE
                        binding.commentCnt.visibility = View.VISIBLE
                        binding.commentIcon.visibility = View.VISIBLE
                    }


                }

            }
        })
    }

    private fun photoSetView(){
        photoVoteAdapter =  PhotoVoteAdapter(this, voteState).apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.normalVoteList.visibility = View.GONE
        binding.photoVoteList.visibility = View.VISIBLE
        binding.photoVoteList.adapter = photoVoteAdapter
    }

    private fun photoSetObserver() {
        viewModel.retrofitViewPolls.observe(this, {

            viewModel.retrofitViewPolls.value?.let { it1 -> photoVoteAdapter.setData(it1)

                binding.title.text = it1.viewPollsItem.contents
                canReVote = it1.viewPollsItem.canRevote
                canComment = it1.viewPollsItem.canComment
                // 익명
                if(it1.viewPollsItem.showNick) binding.nick.text = it1.viewPollsItem.nick else binding.nick.text = "anonymous"


                // 투표유무
                if (it1.viewPollsItem.myBallots == null || voteState) {
                    binding.voteBtn.visibility = View.VISIBLE
                    binding.reVoteBtn.visibility = View.GONE
                    binding.statisticsBtn.visibility = View.GONE
                    binding.commentList.visibility = View.GONE
                    binding.commentCnt.visibility = View.GONE
                    binding.commentIcon.visibility = View.GONE

                } else{
                    binding.voteBtn.visibility = View.GONE
                    binding.reVoteBtn.visibility = View.VISIBLE

                    if(it1.viewPollsItem.canRevote) binding.statisticsBtn.visibility = View.VISIBLE

                    // 댓글
                    if (it1.viewPollsItem.canComment){
                        binding.commentList.visibility = View.VISIBLE
                        binding.commentCnt.visibility = View.VISIBLE
                        binding.commentIcon.visibility = View.VISIBLE
                    }


                }


            }
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

            viewModel.retrofitCommentList.value?.let { it1 -> commentsAdapter.setData(it1)
                binding.commentCnt.text = it1.commentItem.size.toString()
            }
        })
    }


    /***
     * 투표를 한 후에 background, text 색이 변해야한다.
     * Activity에서 색 변화를 진행할 경우 background만 변화 가능
     * Adapter에서 색 변화를 진행할 경우 background, text 색 모두 변화시 킬 수 있지만 다른 item의 색을 변화시킬 수 없어 원래 상태로 바꿀 수 없음.
     * 어떤식으로 해결해야할지 고민이 필요함..
     */
    override fun onNormalVoteClickListener(
        item: ItemComponent,
        position: Int,
        isSingleVote: Boolean,
        myBallots: ArrayList<Int>?
    ) {
        // 투표를 하지 않았던가, 재투표를 해야한다면
        if (myBallots == null || voteState){
            // 단일 투표
            if (isSingleVote){
                // 투표하지 않았다면
                if (checkVote.find { it == item.itemNum } == null) {
                    binding.normalVoteList.children.iterator().forEach { item ->

                        item.setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
                    }
                    checkVote.clear()
                    checkVote.add(item.itemNum)
                    binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)

                } else {

                    binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
                    checkVote.remove(item.itemNum)
                }
                // 중복 투표
            }else{
                // 투표를 안한 것이라면
                if (checkVote.find { it == item.itemNum } == null) {
                    binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)
                    checkVote.add(item.itemNum)

                    // 투표한 것이라면
                } else {

                    binding.normalVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
                    checkVote.remove(item.itemNum)

                }
            }
        }

    }

    override fun onPhotoVoteClickListener(
        item: ItemComponent,
        position: Int,
        isSingleVote: Boolean,
        myBallots: ArrayList<Int>?
    ) {

        if (myBallots == null || voteState){
            // 단일 투표
            if (isSingleVote){
                // 투표하지 않았다면
                if (checkVote.find { it == item.itemNum } == null) {
                    binding.photoVoteList.children.iterator().forEach { item ->

                        item.setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
                    }

                    checkVote.clear()
                    checkVote.add(item.itemNum)
                    binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)


                } else {

                    binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
                    checkVote.remove(item.itemNum)
                }

                // 중복 투표
            }else{

                // 투표를 안한 것이라면
                if (checkVote.find { it == item.itemNum } == null) {
                    checkVote.add(item.itemNum)
                    binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_select_normal_poll_background)

                    // 투표한 것이라면
                } else {

                    binding.photoVoteList[position].setBackgroundResource(R.drawable.layout_unselect_normal_poll_background)
                    checkVote.remove(item.itemNum)
                }


            }
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
                startActivity( Intent(this@VoteActivity, HomeActivity::class.java))

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