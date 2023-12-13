package repository

import pojo.Quotes
import util.ApiQuotes

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