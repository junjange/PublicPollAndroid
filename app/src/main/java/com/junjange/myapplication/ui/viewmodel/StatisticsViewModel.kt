package com.junjange.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.junjange.myapplication.data.*
import com.junjange.myapplication.repository.StatRepository
import kotlinx.coroutines.launch

class StatisticsViewModel (private val repository: StatRepository) : ViewModel(){

    private val _retrofitStat= MutableLiveData<Stat>()


    // LiveData
    val retrofitStat: MutableLiveData<Stat>
        get() = _retrofitStat


    fun getStatRetrofit(pollId : Int, ageOption : Int, genderOption: Int, tierOption : Int  ) = viewModelScope.launch {
        val response = repository.retrofitStat(StatReqItem(pollId, ageOption, genderOption, tierOption))
        if (response.isSuccessful) _retrofitStat.value = response.body()
    }



    class Factory(private val application : Application) : ViewModelProvider.Factory { // factory pattern
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return StatisticsViewModel(StatRepository.getInstance(application)!!) as T
        }
    }




}