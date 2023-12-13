package com.example.quotes.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotes.R
import com.example.quotes.databinding.ActivityFavoriteBinding
import com.example.quotes.ui.ViewModel.SharedViewModel
import com.example.quotes.ui.adapter.QuotesAdapter


class FavoriteActivity : AppCompatActivity(){
    private lateinit var bindingFavortie: ActivityFavoriteBinding
    private lateinit var adapter: QuotesAdapter
    private lateinit var quotesList:MutableList<String>
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingFavortie=DataBindingUtil.setContentView(this,R.layout.activity_favorite)
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)
        initRecyclerView()
        observeFavoriteQuotes()
    }

    private fun observeFavoriteQuotes() {
        sharedViewModel.favoriteQuotes.observe(this) { favoriteQuotes ->
            Log.d("FavoriteActivity", "Favorite quotes updated: $favoriteQuotes")
//            adapter.updateQuotes(favoriteQuotes)
        }
    }
    private fun initRecyclerView() {
//        bindingFavortie.recyclerQuotes.layoutManager = LinearLayoutManager(this)
//        bindingFavortie.recyclerQuotes.setHasFixedSize(true)

//        val quotesList = listOf(
//            Quotes("1", "Author 1", "Content 1", listOf("tag1", "tag2"), "author_slug_1", 42, "2023-01-01", "2023-01-02"),
//            Quotes("2", "Author 2", "Content 2", listOf("tag3", "tag4"), "author_slug_2", 64, "2023-02-01", "2023-02-02"),
//        )
        receiveQuotes()
//        bindingFavortie.recyclerQuotes.adapter = adapter
    }
    private fun receiveQuotes(){
        // Retrieve the text data from the Intent
        val newQuoteText = intent.getStringExtra("quoteContent")
        // Initialize the list if null
        if (newQuoteText != null) {
            quotesList = mutableListOf(newQuoteText)
        } else {
            quotesList = mutableListOf()
        }
        // Set up your RecyclerView with the adapter
        adapter = QuotesAdapter(quotesList)
        bindingFavortie.recyclerQuotes.adapter = adapter
        bindingFavortie.recyclerQuotes.layoutManager = LinearLayoutManager(this)
        bindingFavortie.recyclerQuotes.setHasFixedSize(true)

    }
}
