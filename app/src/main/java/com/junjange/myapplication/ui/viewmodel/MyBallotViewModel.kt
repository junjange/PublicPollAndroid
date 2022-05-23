package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.MyBallot
import com.junjange.myapplication.repository.MyBallotRepository
import kotlinx.coroutines.launch

class MyBallotViewModel (private val repository: MyBallotRepository) : ViewModel(){
    private val _retrofitMyBallot= MutableLiveData<MyBallot>()

    // LiveData
    val retrofitMyBallot: MutableLiveData<MyBallot>
        get() = _retrofitMyBallot

    init { // 초기화 시 서버에서 데이터를 받아옵니다.
        viewModelScope.launch {
            _retrofitMyBallot.value = repository.retrofitMyBallot()
        }
    }


    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return MyBallotViewModel(MyBallotRepository.getInstance(application)!!) as T
        }
    }


}