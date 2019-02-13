package app

import de.compeople.swn.tarifService.KeyValueStore
import kotlin.browser.localStorage

class KeyValueStoreWeb : KeyValueStore {

    override fun setValue(key: String, value: String) {
        return localStorage.setItem(key, value)
    }

    override fun getValue(key: String): String? {
        return localStorage.getItem(key)
    }

}
