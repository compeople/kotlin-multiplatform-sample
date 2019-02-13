package de.compeople.swn.tarifService

import de.compeople.swn.time.Timestamp
import kotlinx.serialization.Serializable

@Serializable
data class Tarif(val entries: List<TarifEntry>, val timestamp: Timestamp) {

    fun tarifForAge(age: Int): TarifEntry = entries.sortedByDescending { it.age }.first { it.age <= age }

    fun tarifMinAge(): Int = entries.sortedBy { it.age }.first().age

}

@Serializable
data class TarifEntry(val age: Int, val tarifFemale: Int, val tarifMale: Int, val tarifDivers: Int)


