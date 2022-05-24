package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.HotPolls
import com.junjange.myapplication.repository.HotPollsRepository
import kotlinx.coroutines.launch

class HotPollsViewModel (private val repository: HotPollsRepository) : ViewModel() {

    private val _retrofitHotPolls = MutableLiveData<HotPolls>()

    // LiveData
    val retrofitHotPolls: MutableLiveData<HotPolls>
        get() = _retrofitHotPolls

    init { // 초기화 시 서버에서 데이터를 받아옵니다.
        viewModelScope.launch {
            _retrofitHotPolls.value = repository.retrofitHotPolls()
        }
    }


    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HotPollsViewModel(HotPollsRepository.getInstance(application)!!) as T
        }
    }


}