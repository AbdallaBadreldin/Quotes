package ui

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.quotes.R
import com.example.quotes.databinding.ActivityFavoriteBinding

import com.example.quotes.ui.adapter.QuotesAdapter
import com.google.gson.Gson
import pojo.Quotes
import repository.Credential
import storage.SharedPreferencesManager

class FavoriteActivity : AppCompatActivity() {
    private lateinit var bindingFavorite:ActivityFavoriteBinding
    private lateinit var quotesAdapter:QuotesAdapter
    private lateinit var favoriteQuotesList: List<Quotes>
    private val quotesList = mutableListOf<Quotes>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         bindingFavorite = DataBindingUtil.setContentView(this, R.layout.activity_favorite)

        initRecyclerView()

    }
    private fun initRecyclerView() {
        // Assuming you have a function to get the list of favorite quotes
        favoriteQuotesList = getFavoriteQuotes()
//               val loadData =  receiveQuotes()
        quotesAdapter = QuotesAdapter(favoriteQuotesList)
        bindingFavorite.recyclerQuotes.layoutManager = LinearLayoutManager(this)
        bindingFavorite.recyclerQuotes.setHasFixedSize(true)
        bindingFavorite.recyclerQuotes.adapter = quotesAdapter
        //call method retrieveQuotes() to get save quotes from shared preference.

    }

    private fun receiveQuotes(): List<Quotes> {
        val quotesList = mutableListOf<Quotes>()
        val preferences: SharedPreferences = getSharedPreferences(Credential.PREF_KEY,
            AppCompatActivity.MODE_PRIVATE
        )
        val keys: Map<String, *> = preferences.all

        for ((key, value) in keys) {
            // Assuming the keys are in the format "quote_" + timestamp
            if (key.startsWith("quote_") && value is String) {
                // Parse the JSON string into a Quotes object
                val quotesJson = value.toString()
                val quote: Quotes = Gson().fromJson(quotesJson, Quotes::class.java)

                // Add the quote to the list
                quotesList.add(quote)
            }
        }

        return quotesList
    }


//        val sharedPref = getSharedPreferences(Credential.PREF_KEY, MODE_PRIVATE)
//        val keys = sharedPref.all.keys.filter { it.startsWith("quote_") }.map { it.split("_")[1].toInt() }
//        return keys.mapNotNull { content ->
//            val taskJson = sharedPref.getString("quote_$content", null)
//            taskJson?.let { Gson().fromJson(it, Quotes::class.java) }
//        }

    private fun getFavoriteQuotes(): List<Quotes> {
        return listOf(
            Quotes("1", "Author 1", "Quote 1", listOf("Tag1"), "author_slug_1", 30, "date_added", "date_modified", true),
            Quotes("2", "Author 2", "Quote 2", listOf("Tag2"), "author_slug_2", 40, "date_added", "date_modified", true)
        )
    }


}