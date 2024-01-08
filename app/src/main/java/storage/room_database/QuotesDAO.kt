package storage.room_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface QuotesDAO {
   // get all quotes from database
    @Query("SELECT * FROM QUOTES_TABLE ORDER BY id DESC")
    suspend fun getAllQuotesFromData():List<QuotesEntity>
    // insertQuotes
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertQuotesIntoData(quotesEntity: QuotesEntity)
    //add delete function
    @Delete
    suspend fun deleteQuotesFromData(quotesEntity: QuotesEntity)
    // filter  quotes by content and author
    @Query("SELECT * FROM QUOTES_TABLE WHERE content LIKE :searchQuery OR author LIKE :searchQuery")
    suspend fun searchDatabase(searchQuery: String): List<QuotesEntity>

}