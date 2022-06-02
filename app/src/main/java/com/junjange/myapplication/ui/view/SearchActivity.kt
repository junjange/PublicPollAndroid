package com.junjange.myapplication.ui.view

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.junjange.myapplication.R
import com.junjange.myapplication.adapter.SearchAdapter
import com.junjange.myapplication.databinding.ActivitySearchBinding
import com.junjange.myapplication.ui.viewmodel.SearchViewModel
import com.junjange.myapplication.utils.textChangesToFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext


class SearchActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private var myCoroutineJob : Job = Job()
    private val myCoroutineContext: CoroutineContext
        get() = Dispatchers.IO + myCoroutineJob
    private val viewModel by lazy { ViewModelProvider(this, SearchViewModel.Factory(application))[SearchViewModel::class.java] }
    private lateinit var retrofitAdapter: SearchAdapter

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

        setView() // 리사이클러 뷰 연결
        setObserver() // 뷰모델을 관찰합니다.

        /**
         * coroutine flow debounce 통해 반응형 프로그래밍 구현
         * 검색어 입력시 바로바로 api 호출
         */
        binding.etSearchField.apply {
            this.hint = "# 해시태그로 검색해요!"

            // EditText 에 포커스가 갔을 때 ClearButton 활성화
            this.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus) {

                    binding.textClearButton.visibility = View.VISIBLE
                } else {

                    binding.textClearButton.visibility = View.GONE


                }
            }


            lifecycleScope.launch(context = myCoroutineContext){

                // editText 가 변경되었을때
                val editTextFlow = binding.etSearchField.textChangesToFlow()

                editTextFlow
                    // 연산자들
                    // 입려되고 나서 0.3초 뒤에 받는다
                    .debounce(300)
                        // 필터
//                    .filter {
//                        it?.length!! > 0
//                    }
                    .onEach {
//                        Log.d(ContentValues.TAG, "flow로 받는다 $it 11")

                        lifecycleScope.launch {


                            if (!it.isNullOrBlank()){
                                viewModel.searchRetrofit(it.toString())

                            }else{
                                binding.rvList.visibility = View.GONE
                                binding.noResultCard.visibility = View.VISIBLE
                                binding.textClearButton.visibility = View.GONE



                            }
                        }
                    }
                    .launchIn(this)


            }

        }

        // 검색창 엔터
        binding.etSearchField.setOnKeyListener { _, keyCode, event ->

            if ((event.action== KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // 엔터가 눌릴 때 하고 싶은 일
                binding.etSearchField.clearFocus()
                imm.hideSoftInputFromWindow(binding.etSearchField.windowToken, 0)
                true

            } else {

                false

            }
        }

        // ClearButton 눌렀을 때 쿼리 Clear
        binding.textClearButton.setOnClickListener {
            binding.etSearchField.text.clear()
//            imm.showSoftInput(binding.etSearchField,0)

        }
    }


    private fun setView(){
        retrofitAdapter =  SearchAdapter(this).apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.rvList.adapter = retrofitAdapter
    }

    private fun setObserver() {
        viewModel.retrofitSearchList.observe(this, {

            viewModel.retrofitSearchList.value?.let { it1 -> retrofitAdapter.setData(it1)

                if (it1.hashtagNameItem.size == 0){
                    binding.rvList.visibility = View.GONE
                    binding.noResultCard.visibility = View.VISIBLE
                }else{
                    binding.rvList.visibility = View.VISIBLE
                    binding.noResultCard.visibility = View.GONE
                    binding.textClearButton.visibility = View.VISIBLE
                }
            }

        })

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
                startActivity( Intent(this@SearchActivity, HomeActivity::class.java))

            }
            R.id.allPollsDrawer-> {
                startActivity( Intent(this@SearchActivity, PollsActivity::class.java))

            }
            R.id.hotPollsDrawer-> {
                startActivity( Intent(this@SearchActivity, HotPollsActivity::class.java))

            }
//            R.id.searchDrawer-> {
//                startActivity( Intent(this@SearchActivity, SearchActivity::class.java))
//
//            }
            R.id.myPageDrawer-> {
                // My Page 이동
                startActivity( Intent(this@SearchActivity, MyPageActivity::class.java))

            }

            R.id.newPollDrawer-> {
                // My Page 이동
                startActivity(Intent(this@SearchActivity, NewPollActivity::class.java))

            }

            R.id.logoutDrawer-> {
                // 로그아웃
                Firebase.auth.signOut()
                startActivity(Intent(this@SearchActivity, MainActivity::class.java))
            }

        }
        return false
    }

    /**
     * 키보드 이외의 영역을 터치했을 때, 키보드를 숨기는 동작
     */
    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        val view = currentFocus
        if (view != null && (ev!!.action === KeyEvent.ACTION_UP || MotionEvent.ACTION_MOVE === ev!!.action) &&
            view is EditText && !view.javaClass.name.startsWith("android.webkit.")
        ) {
//            binding.noResultCard.visibility = View.GONE

            val scrcoords = IntArray(2)
            view.getLocationOnScreen(scrcoords)
            val x = ev!!.rawX + view.getLeft() - scrcoords[0]
            val y = ev!!.rawY + view.getTop() - scrcoords[1]
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom()) (this.getSystemService(
                INPUT_METHOD_SERVICE
            ) as InputMethodManager).hideSoftInputFromWindow(
                this.window.decorView.applicationWindowToken, 0
            )
        }

        return super.dispatchTouchEvent(ev)
    }


    override fun onDestroy() {
//        Log.d(TAG, "PhotoCollectionActivity - onDestroy() called")
        myCoroutineContext.cancel()  // MemoryLeak 방지를 위해 myCoroutineContext 해제
        super.onDestroy()
    }

    override fun onBackPressed() { //뒤로가기 처리
        if(binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.mainDrawerLayout.closeDrawers()

        } else{
            super.onBackPressed()
        }
    }
}