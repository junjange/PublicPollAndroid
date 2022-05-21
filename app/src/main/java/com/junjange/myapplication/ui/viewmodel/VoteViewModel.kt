package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.ViewPolls
import com.junjange.myapplication.repository.VoteRepository
import kotlinx.coroutines.launch

class VoteViewModel(private val repository: VoteRepository, private val pollId : Int) : ViewModel(){
    private val _retrofitViewPolls = MutableLiveData<ViewPolls>()
//    private val _retrofitCommentList = MutableLiveData<ViewPolls>()

    // LiveData
    val retrofitViewPolls: MutableLiveData<ViewPolls>
        get() = _retrofitViewPolls

//
//    val retrofitCommentList: MutableLiveData<ViewPolls>
//        get() = _retrofitCommentList

    init { // 초기화 시 서버에서 데이터를 받아옵니다.
        viewModelScope.launch {
            _retrofitViewPolls.value = repository.retrofitViewPolls(pollId)
        }

//        viewModelScope.launch {
//            _retrofitCommentList.value = repository.retrofitSelectAllTodo()
//        }
    }


    class Factory(private val application : Application, private val pollId: Int) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return VoteViewModel(VoteRepository.getInstance(application)!!, pollId) as T
        }
    }




}