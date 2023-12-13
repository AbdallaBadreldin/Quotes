package com.example.quotes.ui.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pojo.Quotes
import repository.QuotesRepository

class QuotesViewModel(
    private val quotesRepository: QuotesRepository
):ViewModel() {
    private val _quotes = MutableLiveData<List<Quotes>>()
    val quotes: LiveData<List<Quotes>> get() = _quotes

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error



    private val _favoriteQuotes = MutableLiveData<List<Quotes>>()
    val favoriteQuotes: LiveData<List<Quotes>> get() = _favoriteQuotes

    fun loadQuotes() {
        viewModelScope.launch {
            try {
                val quotesList = quotesRepository.getQuotes()
                _quotes.value = quotesList
            } catch (e: Exception) {
                val errorMessage = "Error loading quotes: ${e.message}"
                _error.value = errorMessage
                Log.e("QuotesViewModel", errorMessage, e)
            }
        }
    }

    fun addToFavorites(quote: Quotes) {
        // Add the quote to the list of favorite quotes
        val currentList = _favoriteQuotes.value.orEmpty().toMutableList()
        currentList.add(quote)
        _favoriteQuotes.value = currentList
    }


}