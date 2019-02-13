package de.compeople.swn

import android.content.SharedPreferences
import de.compeople.swn.tarifService.KeyValueStore

class KeyValueStoreAndroid(private val sharedPrefs: SharedPreferences) : KeyValueStore {

    override fun getValue(key: String): String? =
            sharedPrefs.getString(key, null)

    override fun setValue(key: String, value: String) {
        sharedPrefs.edit().putString(key, value).apply()
    }

}