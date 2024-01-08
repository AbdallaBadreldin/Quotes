package util

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import repository.QuotesRepository
import storage.room_database.QuotesDAO
import storage.room_database.QuotesDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDataService {
    @Singleton
    @Provides
    fun provideQuotesDatabaseBuilder(application:Application): QuotesDatabase {
        return Room.databaseBuilder(
            application,
            QuotesDatabase::class.java, "quotes_database"
        ).allowMainThreadQueries().build(
        )
    }
    @Singleton
    @Provides
    fun provideQuotesInstance(quotesDatabase: QuotesDatabase): QuotesDAO {
        return quotesDatabase.quotesDatabaseDao
    }
//    @Singleton
//    @Provides
//    fun provideQuotesRepository(apiQuotes: ApiQuotes,quotesDAO: QuotesDAO): QuotesRepository {
//        return QuotesRepository(apiQuotes,quotesDAO)
//    }
}