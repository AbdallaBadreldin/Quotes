package storage.room_database

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class QuotesTypeConverter {
    @TypeConverter
    fun fromQuoteListToString(listOfQuoteTags: List<String>): String {
        return Json.encodeToString(listOfQuoteTags)
    }

    @TypeConverter
    fun toQuoteListFromString(json: String): List<String> {
        return Json.decodeFromString(json)
    }
}