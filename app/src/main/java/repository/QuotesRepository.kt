package repository

import android.widget.Toast
import kotlinx.coroutines.flow.flow
import pojo.Quotes
import util.ApiQuotes
import util.ApiService
import util.RequestStatus

class QuotesRepository(private val apiQuotes: ApiQuotes) {
    suspend fun getQuotes(): List<Quotes> {
        val response = apiQuotes.getQuotes()
        return if (response.isSuccessful) {
            response.body() ?: emptyList()
        } else {
            // Handle error
            emptyList()
        }
    }


}