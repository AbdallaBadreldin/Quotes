package repository

import android.content.Context
import storage.SharedPreferencesManager

object Credential {
     const val BASE_URL = "https://api.quotable.io/"

     const val PREF_KEY = "Quotes"
     private var instance: SharedPreferencesManager? = null
     @Synchronized
     fun getInstance(context: Context): SharedPreferencesManager =
          instance ?: synchronized(this) {
               SharedPreferencesManager(context).apply {
                    instance = this
               }
          }

}