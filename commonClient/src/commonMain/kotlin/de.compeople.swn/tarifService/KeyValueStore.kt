package de.compeople.swn.tarifService

interface KeyValueStore {

    fun getValue(key: String): String?
    fun setValue(key: String, value: String)

}