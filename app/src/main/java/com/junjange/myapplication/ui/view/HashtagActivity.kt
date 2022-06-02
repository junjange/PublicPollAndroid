package com.junjange.myapplication.ui.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationView
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.HashtagAdapter
import com.junjange.myapplication.databinding.ActivityHashtagBinding
import com.junjange.myapplication.ui.viewmodel.HashtagViewModel

class HashtagActivity  : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private val binding by lazy { ActivityHashtagBinding.inflate(layoutInflater) }
    private val viewModel by lazy {
        ViewModelProvider(
            this,
            HashtagViewModel.Factory(application)
        )[HashtagViewModel::class.java]
    }
    private lateinit var retrofitAdapter: HashtagAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        val id = intent.getSerializableExtra("id") as Int
        val name = intent.getSerializableExtra("name") as String
        Log.d("tt", id.toString())
        viewModel.getHashtagListRetrofit(id)
        binding.realTitle.text = "#$name"


        /**
         * drawer
         *
         * */
        setSupportActionBar(binding.mainToolbar) // 툴바를 액티비티의 앱바로 지정
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 드로어를 꺼낼 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze2_24) // 홈버튼 이미지 변경
        supportActionBar?.setDisplayShowTitleEnabled(false) // 툴바에 타이틀 안보이게
        binding.mainNavigationView.setNavigationItemSelectedListener(this) //navigation 리스너

        setView(this)
        setObserver()

    }

    private fun setView(context: Context) {
        retrofitAdapter = HashtagAdapter(context).apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.rvList.adapter = retrofitAdapter
    }

    private fun setObserver() {
        viewModel.retrofitHashtagList.observe(this, {

            viewModel.retrofitHashtagList.value?.let { it1 -> retrofitAdapter.setData(it1) }
        })

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> { // 메뉴 버튼
                binding.mainDrawerLayout.openDrawer(GravityCompat.START)    // 네비게이션 드로어 열기
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        binding.mainDrawerLayout.closeDrawers()
        when (item.itemId) {
            R.id.mainPageDrawer -> {
                startActivity(Intent(this@HashtagActivity, HomeActivity::class.java))

            }
            R.id.allPollsDrawer -> {
                startActivity(Intent(this@HashtagActivity, PollsActivity::class.java))

            }
            R.id.hotPollsDrawer -> {
                startActivity(Intent(this@HashtagActivity, HotPollsActivity::class.java))

            }
            R.id.searchDrawer -> {
                startActivity(Intent(this@HashtagActivity, SearchActivity::class.java))

            }
            R.id.myPageDrawer -> {
                // My Page 이동
                startActivity( Intent(this@HashtagActivity, MyPageActivity::class.java))

            }

            R.id.newPollDrawer-> {
                // My Page 이동
                startActivity(Intent(this@HashtagActivity, NewPollActivity::class.java))

            }

            R.id.logoutDrawer-> {
                // 로그아웃
//            startActivity(Intent(this@HomeActivity, MyPageActivity::class.java))

            }

        }
        return false
    }

    override fun onBackPressed() { //뒤로가기 처리
        if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawerLayout.closeDrawers()

        } else {
            super.onBackPressed()
        }
    }
}