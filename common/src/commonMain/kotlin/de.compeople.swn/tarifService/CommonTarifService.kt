package de.compeople.swn.tarifService

interface CommonTarifService {

    suspend fun getTarif(): Tarif

}