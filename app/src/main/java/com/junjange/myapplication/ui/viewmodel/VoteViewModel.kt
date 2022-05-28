package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.*
import com.junjange.myapplication.repository.VoteRepository
import kotlinx.coroutines.launch

class VoteViewModel(private val repository: VoteRepository) : ViewModel(){
    private val _retrofitViewPolls = MutableLiveData<ViewPolls>()
    private val _retrofitCommentList = MutableLiveData<Comment>()
    private val _retrofitBallotPolls = MutableLiveData<Ballot>()


    // LiveData
    val retrofitViewPolls: MutableLiveData<ViewPolls>
        get() = _retrofitViewPolls

    // LiveData
    val retrofitBallotPolls: MutableLiveData<Ballot>
        get() = _retrofitBallotPolls


    val retrofitCommentList: MutableLiveData<Comment>
        get() = _retrofitCommentList



    // 투표 보기
    fun getViewPollsRetrofit(pollId : Int) = viewModelScope.launch {
        _retrofitViewPolls.value = repository.retrofitViewPolls(pollId)
    }

    // 투표 하기
    fun postBallotRetrofit(pollId : Int, itemNum : ArrayList<Int>) = viewModelScope.launch {

        val response = repository.retrofitPostBallot(PostBallotItem(pollId, itemNum))
        if (response.isSuccessful) _retrofitBallotPolls.value = response.body()
        Log.d("ttt2222", response.body().toString())
    }

    // 댓글 보기
    fun getCommentsRetrofit(pollId : Int) = viewModelScope.launch {
        _retrofitCommentList.value = repository.retrofitComments(pollId)
    }

    // 댓글 하기
    fun postCommentRetrofit(pollId : Int, contents : String) = viewModelScope.launch {
        val response = repository.retrofitPostComments(PostCommentItem(pollId, contents))
        if (response.isSuccessful) _retrofitCommentList.value = repository.retrofitComments(pollId)
    }


    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VoteViewModel(VoteRepository.getInstance(application)!!) as T
        }
    }




}