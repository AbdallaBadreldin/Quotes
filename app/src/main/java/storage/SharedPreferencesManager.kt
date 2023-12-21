package storage

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import pojo.Quotes
import repository.Credential

class SharedPreferencesManager(mCtx: Context) {

    private var sharedPreferences: SharedPreferences =
        mCtx.getSharedPreferences(Credential.PREF_KEY, Context.MODE_PRIVATE)

    fun saveQuotes(textQuote: String) {
        val editor = sharedPreferences.edit().apply() {
            val uniqueKey = "quote_" + System.currentTimeMillis()
            putString(uniqueKey, textQuote)

        }
        editor.apply()
    }

    fun loadQuotes(textQuote: String) {
        sharedPreferences.getString("contentQuote", textQuote)
//        sharedPreferences.getString("authorQuote",quotes.author)
    }
}