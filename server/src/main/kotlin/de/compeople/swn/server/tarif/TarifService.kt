package de.compeople.swn.server.tarif

import de.compeople.swn.tarifService.CommonTarifService
import de.compeople.swn.tarifService.Tarif
import de.compeople.swn.tarifService.TarifEntry
import de.compeople.swn.time.Timestamp

object TarifService : CommonTarifService {

    override suspend fun getTarif(): Tarif {
        val randomTarifEntries = listOf(
                TarifEntry(18, getRandomNumber(), getRandomNumber(), getRandomNumber()),
                TarifEntry(25, getRandomNumber(), getRandomNumber(), getRandomNumber()),
                TarifEntry(55, getRandomNumber(), getRandomNumber(), getRandomNumber()))
        return Tarif(randomTarifEntries, Timestamp(System.currentTimeMillis()))
    }

    private fun getRandomNumber(): Int {
        return (100..500).shuffled().first()
    }

}

