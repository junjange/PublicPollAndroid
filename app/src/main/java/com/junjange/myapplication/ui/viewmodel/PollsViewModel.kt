package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.ModelBoard
import com.junjange.myapplication.data.Polls
import com.junjange.myapplication.repository.PollsRepository
import kotlinx.coroutines.launch

class PollsViewModel (private val repository: PollsRepository) : ViewModel(){
    private val _retrofitAllPolls= MutableLiveData<Polls>()

    // LiveData
    val retrofitAllPolls: MutableLiveData<Polls>
        get() = _retrofitAllPolls

    init { // 초기화 시 서버에서 데이터를 받아옵니다.
        viewModelScope.launch {
            _retrofitAllPolls.value = repository.retrofitAllPolls()
        }
    }


    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PollsViewModel(PollsRepository.getInstance(application)!!) as T
        }
    }


}