package com.example.quotes.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import repository.QuotesRepository
import storage.room_database.QuotesDAO

class QuotesViewModelFactory(
    private val quotesRepository: QuotesRepository
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuotesViewModel::class.java)) {
            return QuotesViewModel(quotesRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}