package com.example.quotes.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import pojo.Quotes
import repository.QuotesRepository
import storage.room_database.QuotesDAO
import util.RequestStatus
import javax.inject.Inject


class QuotesViewModel @Inject constructor(
    private val quotesRepository: QuotesRepository
) : ViewModel() {
    private val _isLoad = MutableLiveData<Boolean>().apply { value = false }
    private val _quotes = MutableLiveData<List<Quotes>>()
    private val _error = MutableLiveData<String>()
    val isLoad: LiveData<Boolean> get() = _isLoad
    val quotes: LiveData<List<Quotes>> get() = _quotes
    val error: LiveData<String> get() = _error

    fun loadQuotes() {
        viewModelScope.launch {
            quotesRepository.getQuotes().collect { requestStatus ->
                when (requestStatus) {
                    is RequestStatus.Waiting -> {
                        _isLoad.value = true
                    }
                    is RequestStatus.Success -> {
                        _isLoad.value = false
                        _quotes.value = requestStatus.data.results
                    }
                    is RequestStatus.Error -> {
                        _isLoad.value = false
                        _error.value = requestStatus.message
                    }
                }
            }
        }
    }
}