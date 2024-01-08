package storage.room_database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "QUOTES_TABLE")
data class QuotesEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id:Long,
    val author:String,
    val content:String,
    val tags:List<String>,
    val authorSlug:String,
    val length: Int,
    val dataAdded:String,
    val dataModified:String
)