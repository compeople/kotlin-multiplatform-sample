package de.compeople.swn.tarifService

import kotlinx.serialization.json.Json

private const val TARIF_KEY = "Tarif"


class TarifRepository(private val keyValueStore: KeyValueStore) {

    fun getTarif(): Tarif? {
        return keyValueStore.getValue(TARIF_KEY)?.parseTarifJson()
    }

    fun setTarif(tarif: Tarif) {
        return keyValueStore.setValue(TARIF_KEY, tarif.toJson())
    }

}

fun Tarif.toJson(): String = Json.stringify(Tarif.serializer(), this)

internal fun String.parseTarifJson(): Tarif = Json.parse(Tarif.serializer(), this)

