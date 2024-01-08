package com.example.quotes.ui.quotesFragment

import android.annotation.SuppressLint
import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.quotes.R
import com.example.quotes.databinding.FragmentQuotesBinding
import com.example.quotes.ui.viewmodel.QuotesViewModel
import com.example.quotes.ui.viewmodel.QuotesViewModelFactory
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.qualifiers.ApplicationContext
import repository.QuotesRepository
import storage.SharedPreferencesManager
import storage.room_database.QuotesDAO
import storage.room_database.QuotesDatabase
import util.ApiService
import util.ShareQuotes

class QuotesFragment : Fragment(), View.OnClickListener {
    private lateinit var bindingQuotes: FragmentQuotesBinding
    private lateinit var mViewModel: QuotesViewModel
    // set initial full heart red color
    private var isHeartFull = false
    // set initial background button
    var isWhite = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        bindingQuotes =
            DataBindingUtil.inflate(inflater, R.layout.fragment_quotes, container, false)
//==================================================================================================
        // Instantiate the QuotesDatabase in your Fragment/Activity
//        val dao = QuotesDatabase.getInstance(Application()).quotesDao
//// Create an instance of the QuotesRepository using ApiService and QuotesDAO
//        val repository = QuotesRepository(ApiService.getService(), dao)
//        // Instantiate the ViewModel using the QuotesRepository
//        mViewModel = ViewModelProvider(
//            this,
//            QuotesViewModelFactory(repository)
//        )[QuotesViewModel::class.java]

        mViewModel = ViewModelProvider(
            this,
            QuotesViewModelFactory(QuotesRepository(ApiService.getService()))
        )[QuotesViewModel::class.java]
        //=======================================
        setUpObserver()
        //=======================================
        return bindingQuotes.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        super.onViewCreated(view, savedInstanceState)
        bindingQuotes.btnQutoes.setOnClickListener(this)
        bindingQuotes.btnHeart.setOnClickListener(this)
        bindingQuotes.btnShare.setOnClickListener(this)

    }
    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.btn_Qutoes -> {
                    changeBackgroundButtonQuotes()
                    observeButtonClick()
                }

                R.id.btn_Heart -> {
                    if (bindingQuotes.btnQutoes.text.isEmpty()) {
                        Snackbar.make(
                            requireView(),
                            "Please click button quotes first",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }
                    changeHeartColor()
                }

                R.id.btn_Share -> {
                    if (bindingQuotes.txtQuotes.text.isEmpty()) {
                        Snackbar.make(
                            requireView(),
                            "Please click button quotes first",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        return
                    }
                    changeBackgroundButtonShare()
                    ShareQuotes.shareQuote(
                        bindingQuotes.txtQuotes.text.toString(),
                        requireContext()
                    )
                }
            }
        }
    }

    // implementation when click button quotes to get quotes from api
    private fun setUpObserver() {
        mViewModel.isLoad.observe(viewLifecycleOwner)
        { isLoad ->
            if (isLoad) {
                bindingQuotes.progressBar.visibility = View.VISIBLE
            } else {
                bindingQuotes.progressBar.visibility = View.GONE
            }
        }

        // Observe the quotes LiveData to update the UI with new quotes when the quotes are ready
        mViewModel.quotes.observe(viewLifecycleOwner) { quotesList ->
            //get quotes random from api
            if (quotesList.isNotEmpty()) {
                val shuffledList = quotesList.shuffled()
                val randomQuote = shuffledList.last()
                // Display the content and author in a custom way
                val formattedQuote = "\"${randomQuote.content}\" \n- ${randomQuote.author}"
                bindingQuotes.txtQuotes.text = formattedQuote

            } else {
                bindingQuotes.txtQuotes.text = R.string.no_available.toString()
            }
        }
        // Observe the error LiveData to handle any errors
        mViewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            // Handle error
            Toast.makeText(requireContext(), "Error: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    }

    private fun observeButtonClick() {
        // Call loadQuotes() to fetch new quotes when the button is clicked
        mViewModel.loadQuotes()
    }

    //Save data in shared preference
    private fun saveData() {
        SharedPreferencesManager(requireActivity().baseContext).saveQuotes(bindingQuotes.txtQuotes.text.toString())
    }

    // change heart color when click button heart to red color and return to white color when click again and save data in shared preference
    @SuppressLint("UseCompatLoadingForDrawables")
    private fun changeHeartColor() {
        isHeartFull = !isHeartFull
        // Change heart color or perform other actions here
        val heartDrawable = if (isHeartFull) {
            resources.getDrawable(R.drawable.baseline_read_heart, null)
        } else {
            resources.getDrawable(R.drawable.baseline_favorite_border_24, null)
        }
        bindingQuotes.btnHeart.setCompoundDrawablesWithIntrinsicBounds(
            null,
            null,
            heartDrawable,
            null
        )
        saveData()
    }

    //change background button share when click button share
    private fun changeBackgroundButtonShare() {
        isWhite = !isWhite
        if (isWhite) {
            bindingQuotes.btnShare.setBackgroundColor(Color.WHITE)
        } else {
            bindingQuotes.btnShare.setBackgroundColor(resources.getColor(R.color.backgroundColor))
        }
    }

    //change background button quotes when click button quotes
    private fun changeBackgroundButtonQuotes() {
        isWhite = !isWhite
        if (isWhite) {
            bindingQuotes.btnQutoes.setBackgroundColor(Color.WHITE)
        } else {
            bindingQuotes.btnQutoes.setBackgroundColor(resources.getColor(R.color.backgroundColor))
        }
    }
}