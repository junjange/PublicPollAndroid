package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.MyPolls
import com.junjange.myapplication.repository.MyPollsRepository
import kotlinx.coroutines.launch

class MyPollsViewModel(private val repository: MyPollsRepository) : ViewModel(){
    private val _retrofitMyPolls= MutableLiveData<MyPolls>()

    // LiveData
    val retrofitMyPolls: MutableLiveData<MyPolls>
        get() = _retrofitMyPolls

    init { // 초기화 시 서버에서 데이터를 받아옵니다.
        viewModelScope.launch {
            _retrofitMyPolls.value = repository.retrofitMyPolls()
        }
    }

    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyPollsViewModel(MyPollsRepository.getInstance(application)!!) as T
        }
    }


}