package util

import pojo.Quotes
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET



interface ApiQuotes {
    @GET("quotes/random")
   suspend fun getQuotes(): Response<List<Quotes>>
}