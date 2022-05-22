package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.Comment
import com.junjange.myapplication.data.HashtagName
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.ViewPolls
import com.junjange.myapplication.network.PollsObject
import com.junjange.myapplication.repository.VoteRepository
import kotlinx.coroutines.launch
import java.util.ArrayList

class VoteViewModel(private val repository: VoteRepository) : ViewModel(){
    private val _retrofitViewPolls = MutableLiveData<ViewPolls>()
    private val _retrofitCommentList = MutableLiveData<Comment>()

    // LiveData
    val retrofitViewPolls: MutableLiveData<ViewPolls>
        get() = _retrofitViewPolls


    val retrofitCommentList: MutableLiveData<Comment>
        get() = _retrofitCommentList

//    init { // 초기화 시 서버에서 데이터를 받아옵니다.
//        viewModelScope.launch {
//            _retrofitViewPolls.value = repository.retrofitViewPolls()
//        }

//        viewModelScope.launch {
//            _retrofitCommentList.value = repository.retrofitSelectAllTodo()
//        }
//    }

    // 일반 투표
    fun getViewPollsRetrofit(pollId : Int) = viewModelScope.launch {
        retrofitViewPolls.value = repository.retrofitViewPolls(pollId)
    }

    // 댓글
    fun getCommentsRetrofit(pollId : Int) = viewModelScope.launch {
        retrofitCommentList.value = repository.retrofitComments(pollId)
    }



    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VoteViewModel(VoteRepository.getInstance(application)!!) as T
        }
    }




}