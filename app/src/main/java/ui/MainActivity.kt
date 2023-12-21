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
import com.example.quotes.ui.ViewModel.QuotesViewModel
import com.example.quotes.ui.ViewModel.QuotesViewModelFactory
import repository.QuotesRepository
import storage.SharedPreferencesManager
import util.ApiService


class MainActivity : AppCompatActivity() , View.OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mViewModel: QuotesViewModel
    private var isHeartFull = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.btnFavorite.setOnClickListener(this)
        binding.btnQutoes.setOnClickListener(this)
        binding.btnHeart.setOnClickListener(this)
        binding.btnShare.setOnClickListener(this)
//===========================================================================
        mViewModel = ViewModelProvider(
            this,
            QuotesViewModelFactory(QuotesRepository(ApiService.getService()))
        )[QuotesViewModel::class.java]
        setUpObserver()
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

                R.id.btn_Qutoes -> {
                    observeButtonClick()
                }

                R.id.btn_Heart -> {
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
                    saveData()
//                    onSendQuotes()
                }
                R.id.btn_Share->{

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
    private fun saveData(){
        SharedPreferencesManager(application.baseContext).saveQuotes(binding.txtQuotes.text.toString())
    }

//    private fun saveData(){
//        // Save the quote text to SharedPreferences
//        val preferences = getSharedPreferences(Credential.PREF_KEY, MODE_PRIVATE)
//        val editor = preferences.edit()
//// Use a unique key for each quote
//        val quoteText = binding.txtQuotes.text.toString()
//        val uniqueKey = "quote_" + System.currentTimeMillis()
//        editor.putString(uniqueKey, quoteText)
//        editor.apply()
//    }

}
