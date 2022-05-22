package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.HashtagName
import com.junjange.myapplication.repository.SearchRepository
import com.junjange.myapplication.network.PollsObject
import kotlinx.coroutines.launch
import retrofit2.Response

class SearchViewModel(private val repository: SearchRepository) : ViewModel(){
    private val _retrofitSearchList = MutableLiveData<HashtagName>()

    // LiveData
    val retrofitSearchList: MutableLiveData<HashtagName>
        get() = _retrofitSearchList

    fun insertRetrofit(keyword : String) = viewModelScope.launch {
        retrofitSearchList.value = repository.retrofitSearch(keyword)
    }

    class Factory(private val application: Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SearchViewModel(SearchRepository.getInstance(application)!!) as T
        }
    }


}