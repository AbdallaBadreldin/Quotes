package com.example.quotes.ui.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pojo.Quotes

class SharedViewModel: ViewModel() {
    private val _favoriteQuotes = MutableLiveData<MutableList<Quotes>>(mutableListOf())
    val favoriteQuotes: LiveData<MutableList<Quotes>> get() = _favoriteQuotes

    fun addFavoriteQuote(quote: Quotes) {
        _favoriteQuotes.value?.add(quote)
        _favoriteQuotes.value = _favoriteQuotes.value // Trigger LiveData update
        Log.d("SharedViewModel", "Favorite quotes updated: ${_favoriteQuotes.value}")

    }
}