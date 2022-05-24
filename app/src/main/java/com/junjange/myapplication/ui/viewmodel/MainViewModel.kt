package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.QuickPolls
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

//    fun insertRetrofit(keyword : String) = viewModelScope.launch {
//        retrofitSearchList.value = repository.retrofitSearch(keyword)
//    }




    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MainViewModel(QuickVoteRepository.getInstance(application)!!) as T
        }
    }




}