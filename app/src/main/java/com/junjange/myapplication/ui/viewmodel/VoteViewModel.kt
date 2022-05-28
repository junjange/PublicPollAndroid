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


    // LiveData
    val retrofitViewPolls: MutableLiveData<ViewPolls>
        get() = _retrofitViewPolls

    val retrofitCommentList: MutableLiveData<Comment>
        get() = _retrofitCommentList



    // 투표 보기
    fun getViewPollsRetrofit(pollId : Int) = viewModelScope.launch {
        _retrofitViewPolls.value = repository.retrofitViewPolls(pollId)
    }

    // 투표 하기
    fun postBallotRetrofit(pollId : Int, itemNum : ArrayList<Int>) = viewModelScope.launch {
        repository.retrofitPostBallot(PostBallotItem(pollId, itemNum))
    }

    // 재투표 하기
    fun postReVoteRetrofit(pollId : Int, itemNum : ArrayList<Int>) = viewModelScope.launch {

        repository.retrofitPostReVote(PostBallotItem(pollId, itemNum))
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