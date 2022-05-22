package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.Hashtag
import com.junjange.myapplication.data.Polls
import com.junjange.myapplication.repository.HashtagRepository
import kotlinx.coroutines.launch

class HashtagViewModel(private val repository: HashtagRepository, private val id: Int) : ViewModel(){
    private val _retrofitHashtagList= MutableLiveData<Hashtag>()

    // LiveData
    val retrofitHashtagList: MutableLiveData<Hashtag>
        get() = _retrofitHashtagList

    init { // 초기화 시 서버에서 데이터를 받아옵니다.
        viewModelScope.launch {
            _retrofitHashtagList.value = repository.retrofitHashtag(id)
        }
    }


    class Factory(private val application : Application, private val  id: Int) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HashtagViewModel(HashtagRepository.getInstance(application)!!, id) as T
        }
    }


}