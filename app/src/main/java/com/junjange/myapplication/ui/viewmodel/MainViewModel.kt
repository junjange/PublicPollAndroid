package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.*
import com.junjange.myapplication.repository.QuickVoteRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: QuickVoteRepository) : ViewModel(){
    private val _retrofitQuickPolls = MutableLiveData<QuickPolls>()

    // LiveData
    val retrofitQuickPolls: MutableLiveData<QuickPolls>
        get() = _retrofitQuickPolls


    init { // 초기화 시 서버에서 데이터를 받아옵니다.
        viewModelScope.launch {

            _retrofitQuickPolls.value = repository.retrofitQuickPolls()

        }
    }

    // 투표 하기
    fun postBallotRetrofit(pollId : Int, itemNum : ArrayList<Int>) = viewModelScope.launch {

        val response = repository.retrofitPostBallot(PostBallotItem(pollId, itemNum))
        if(response.isSuccessful) _retrofitQuickPolls.value = repository.retrofitQuickPolls()
        Log.d("ttt", response.body().toString())
    }


    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(QuickVoteRepository.getInstance(application)!!) as T
        }
    }






}