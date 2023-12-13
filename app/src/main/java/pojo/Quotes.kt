package pojo

import android.os.Parcel
import android.os.Parcelable

data class Quotes(
    val _id: String,
    val author: String,
    val content: String,
    val tags: List<String>,
    val authorSlug: String,
    val length: Int,
    val dateAdded: String,
    val dateModified: String,

    var isFavorite: Boolean = false  // New property to track whether the quote is a favorite

): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.createStringArrayList()!!,
        parcel.readString() ?: "",
        parcel.readInt() ?: 0,
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(_id)
        dest.writeString(author)
        dest.writeString(content)
        dest.writeStringList(tags)
        dest.writeString(authorSlug)
        dest.writeInt(length)
        dest.writeString(dateAdded)
        dest.writeString(dateModified)
        dest.writeByte(if (isFavorite) 1 else 0)
    }

    companion object CREATOR : Parcelable.Creator<Quotes> {
        override fun createFromParcel(parcel: Parcel): Quotes {
            return Quotes(parcel)
        }

        override fun newArray(size: Int): Array<Quotes?> {
            return arrayOfNulls(size)
        }
    }

}

