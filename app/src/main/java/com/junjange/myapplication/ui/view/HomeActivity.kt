package com.junjange.myapplication.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.QuickVoteAdapter
import com.junjange.myapplication.data.QuickPollsItem
import com.junjange.myapplication.databinding.ActivityHomeBinding
import com.junjange.myapplication.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.*

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener ,QuickVoteAdapter.ItemClickListener{

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, HomeViewModel.Factory(application))[HomeViewModel::class.java] }
    private lateinit var retrofitAdapter: QuickVoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        /**
         * drawer
         *
         * */
        setSupportActionBar(binding.mainToolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        binding.mainNavigationView.setNavigationItemSelectedListener(this) //navigation 리스너


        setView()
        setObserver()

        // 투표 검색 페이지로 이동
        binding.searchBtn.setOnClickListener {
            startActivity(Intent(this@HomeActivity, SearchActivity::class.java))

        }

        // 핫 투표 페이지로 이동
        binding.hotPollsBtn.setOnClickListener {
            startActivity( Intent(this@HomeActivity, HotPollsActivity::class.java))

        }

        // 모든 투표 페이지로 이동
        binding.allPollsBtn.setOnClickListener {
            startActivity(Intent(this@HomeActivity, PollsActivity::class.java))

        }

        // 투표 생성 페이지로 이동
        binding.newPollBtn.setOnClickListener {
            startActivity(Intent(this@HomeActivity, NewPollActivity::class.java))

        }

    }

    private fun setView(){
        retrofitAdapter =  QuickVoteAdapter(this).apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.rvList.adapter = retrofitAdapter
    }

    private fun setObserver() {

        viewModel.retrofitQuickPolls.observe(this, {

            viewModel.retrofitQuickPolls.value?.let { it1 -> retrofitAdapter.setData(it1)
            }
        })

    }

    // 투표 하기
    override fun onQuickVoteClickListener(
        item: QuickPollsItem,
        itemNum: ArrayList<Int>,
        voteState: Boolean
    ) {
        if(voteState) viewModel.postReVoteRetrofit(item.id, itemNum) else viewModel.postBallotRetrofit(item.id, itemNum)


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
//            R.id.mainPageDrawer-> {
//                startActivity( Intent(this@MainActivity, MainActivity::class.java))
//
//            }
            R.id.allPollsDrawer-> {
                startActivity( Intent(this@HomeActivity, PollsActivity::class.java))

            }
            R.id.hotPollsDrawer-> {
                startActivity( Intent(this@HomeActivity, HotPollsActivity::class.java))

            }
            R.id.searchDrawer-> {
                startActivity( Intent(this@HomeActivity, SearchActivity::class.java))

            }
            R.id.myPageDrawer-> {
                // My Page 이동
                startActivity(Intent(this@HomeActivity, MyPageActivity::class.java))

            }

        }
        return false
    }


    // 뒤로가기 2번 눌러야 종료
    private val finishIntervalTime: Long = 2500
    private var backPressedTime: Long = 0
    private var toast: Toast? = null
    override fun onBackPressed() {

        // drawer 종료
        if(binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawerLayout.closeDrawers()

            // 앱 종료
        } else{

            val tempTime = System.currentTimeMillis()
            val intervalTime = tempTime - backPressedTime

            // 뒤로 가기 할 경우 홈 화면으로 이동
            if (intervalTime in 0..finishIntervalTime) {
                super.onBackPressed()
                // 앱 종료시 뒤로가기 토스트 종료
                toast!!.cancel()
                finish()
            } else {
                backPressedTime = tempTime
                toast =
                    Toast.makeText(applicationContext, "'뒤로'버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT)
                toast!!.show()
            }
        }
    }
}