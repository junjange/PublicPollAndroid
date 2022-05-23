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

class HashtagViewModel(private val repository: HashtagRepository) : ViewModel(){
    private val _retrofitHashtagList= MutableLiveData<Hashtag>()

    // LiveData
    val retrofitHashtagList: MutableLiveData<Hashtag>
        get() = _retrofitHashtagList


    fun getHashtagListRetrofit(pollId : Int) = viewModelScope.launch {
        retrofitHashtagList.value = repository.retrofitHashtag(pollId)
    }




    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HashtagViewModel(HashtagRepository.getInstance(application)!!) as T
        }
    }


}