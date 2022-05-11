package com.junjange.myapplication.ui.view

import android.content.ContentValues
import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import com.junjange.myapplication.adapter.BoardRecyclerAdapter
import com.junjange.myapplication.databinding.ActivitySearchBinding
import com.junjange.myapplication.ui.viewmodel.SearchViewModel
import com.junjange.myapplication.util.textChangesToFlow
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlin.coroutines.CoroutineContext

@FlowPreview
@ExperimentalCoroutinesApi
@DelicateCoroutinesApi
class SearchActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val viewModel by lazy { ViewModelProvider(this, SearchViewModel.Factory(application))[SearchViewModel::class.java] }
    private lateinit var retrofitAdapter: BoardRecyclerAdapter


    private var myCoroutineJob : Job = Job()
    private val myCoroutineContext: CoroutineContext
        get() = Dispatchers.IO + myCoroutineJob

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // 키보드 설정
        var imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager


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


            GlobalScope.launch(context = myCoroutineContext){

                // editText 가 변경되었을때
                val editTextFlow = binding.etSearchField.textChangesToFlow()

                editTextFlow
                    // 연산자들
                    // 입려되고 나서 0.8초 뒤에 받는다
                    .debounce(800)
                    .filter {
                        it?.length!! > 0
                    }
                    .onEach {
                        Log.d(ContentValues.TAG, "flow로 받는다 $it")
                        // 해당 검색어로 api 호출

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
            imm.showSoftInput(binding.etSearchField,0)

        }
    }


    private fun setView(){
        retrofitAdapter =  BoardRecyclerAdapter().apply {
            setHasStableIds(true) // 리사이클러 뷰 업데이트 시 깜빡임 방지
        }
        binding.rvList.adapter = retrofitAdapter
    }

    private fun setObserver() {
        viewModel.retrofitTodoList.observe(this, {

            viewModel.retrofitTodoList.value?.let { it1 -> retrofitAdapter.setData(it1) }
        })

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
        Log.d(TAG, "PhotoCollectionActivity - onDestroy() called")
        myCoroutineContext.cancel()  // MemoryLeak 방지를 위해 myCoroutineContext 해제
        super.onDestroy()
    }
}