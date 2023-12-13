package ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.R
import com.example.quotes.databinding.ActivityMainBinding
import com.example.quotes.ui.FavoriteActivity
import com.example.quotes.ui.ViewModel.QuotesViewModel
import com.example.quotes.ui.ViewModel.QuotesViewModelFactory
import com.example.quotes.ui.ViewModel.SharedViewModel
import repository.QuotesRepository
import util.ApiService


class MainActivity : AppCompatActivity() , View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: QuotesViewModel
    private lateinit var sharedViewModel: SharedViewModel
    private var isHeartFull = false
    private lateinit var dataQuotes:ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnFavorite.setOnClickListener(this)
        binding.btnQutoes.setOnClickListener(this)
        binding.btnHeart.setOnClickListener(this)
//===========================================================================
        sharedViewModel = ViewModelProvider(this)[SharedViewModel::class.java]

        mViewModel = ViewModelProvider(
            this,
            QuotesViewModelFactory(QuotesRepository(ApiService.getService()))
        )[QuotesViewModel::class.java]
        setUpObserver()
        saveData()
    } //end onCreate

    private fun setUpObserver() {
        mViewModel.quotes.observe(this) { quotesList ->
            Log.d("QuotesViewModel", "Received quotes: $quotesList")
            // Update your UI with the new list of quotes
            // For example, you can set the quotes to a TextView
            val quotesText = quotesList.joinToString("\n\n") { quote ->
                "${quote.content}\n- ${quote.author}"
            }
            binding.txtQuotes.text = quotesText
        }

        // Observe the error LiveData to handle any errors
        mViewModel.error.observe(this) { errorMessage ->
            // Handle error, for example, show a toast or log the error
            Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeButtonClick() {
        // Call loadQuotes() to fetch new quotes when the button is clicked
        mViewModel.loadQuotes()

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_favorite -> {
                    GoToNavFavorite()
                }

                R.id.btn_qutoes -> {
                    observeButtonClick()
                }

                R.id.btn_heart -> {
                    // Toggle heart state
                    isHeartFull = !isHeartFull
                    // Change heart color or perform other actions here
                    val heartDrawable = if (isHeartFull) {
                        resources.getDrawable(R.drawable.baseline_read_heart, null)
                    } else {
                        resources.getDrawable(R.drawable.baseline_favorite_border_24, null)
                    }

                    binding.btnHeart.setCompoundDrawablesWithIntrinsicBounds(
                        null,
                        null,
                        heartDrawable,
                        null
                    )

                    onSendQuotes()
                    // Add the current quote to the shared view model
                    mViewModel.quotes.value?.let {
                        val quoteToAdd = it[0]
                        Log.d("MainActivity", "Adding quote to favorites: $quoteToAdd")
                        sharedViewModel.addFavoriteQuote(quoteToAdd)
                    }
                }
            }
        }
    }
/*This method implementation when click button Favorite go to Favorite.class */
    private fun GoToNavFavorite() {
        startActivity(Intent(this@MainActivity, FavoriteActivity::class.java))
    }

    private fun onSendQuotes(){
        val contentQuotes = binding.txtQuotes.text.toString()
        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
        intent.putExtra("quoteContent", contentQuotes)
        startActivity(intent)
    }
//
    private fun saveData(){
        // Save the quote text to SharedPreferences
        val preferences = getSharedPreferences("Quotes", MODE_PRIVATE)
        val editor = preferences.edit()
// Use a unique key for each quote
        val quoteText = binding.txtQuotes.text.toString()
        val uniqueKey = "quote_" + System.currentTimeMillis()
        editor.putString(uniqueKey, quoteText)
        editor.apply()
    }



}
