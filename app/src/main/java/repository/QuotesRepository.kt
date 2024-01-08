package repository

import androidx.room.Room
import kotlinx.coroutines.flow.flow
import pojo.QuotesResponse
import retrofit2.Response
import storage.room_database.QuotesDAO
import storage.room_database.QuotesDatabase
import util.ApiQuotes
import util.RequestStatus
import javax.inject.Inject



class QuotesRepository @Inject constructor(
    private val apiQuotes: ApiQuotes
) {
    //call interface QuotesDAO
    lateinit var quotesDAO: QuotesDAO
    //get quotes from api
    suspend fun getQuotes() = flow {
        emit(RequestStatus.Waiting)
        try {
            val response: Response<QuotesResponse> = apiQuotes.getQuotes()

            if (response.isSuccessful) {
                emit(RequestStatus.Success(response.body()!!))
            } else {
                emit(
                    RequestStatus.Error(
                        response.errorBody()?.byteStream()?.reader()?.readText()
                            ?: "Unknown error"
                    )
                )
            }
        } catch (e: Exception) {
            emit(RequestStatus.Error(e.message ?: "An error occurred"))
        }
    }

}