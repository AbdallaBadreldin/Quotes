package storage.room_database

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import javax.inject.Inject
import javax.inject.Singleton


@Database(entities = [QuotesEntity::class], version = 1, exportSchema = false)
@TypeConverters(QuotesTypeConverter::class)
abstract class QuotesDatabase(
) : RoomDatabase() {
    // Provide the implementation for the DAO
    abstract val quotesDatabaseDao: QuotesDAO
//    companion object {
//        @Volatile
//        private var INSTANCE: QuotesDatabase? = null
//        private val QUOTESDATA_NAME="QUOTES_DATABASE"
//
//        fun getInstance(context: Context): QuotesDatabase {
//            return INSTANCE ?: synchronized(this) {
//                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
//            }
//        }
//        private fun buildDatabase(context: Context): QuotesDatabase {
//            return Room.databaseBuilder(
//                context.applicationContext,
//                QuotesDatabase::class.java,
//                QUOTESDATA_NAME
//            ).build()
//        }
//    }
}

