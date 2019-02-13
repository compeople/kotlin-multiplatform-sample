package de.compeople.swn.tarifService

import de.compeople.swn.time.TimeService
import de.compeople.swn.time.Timestamp

class TarifService(private val tarifRepository: TarifRepository, private val tarifClient: TarifClient,
                   val timeService: TimeService) : CommonTarifService {

    override suspend fun getTarif(): Tarif {

        val tarifForDevice = tarifRepository.getTarif()
        if (tarifForDevice != null && tarifForDevice.isNotOutdated(timeService)) {
            return tarifForDevice
        }
        val tarif = tarifClient.getTarif()
        tarifRepository.setTarif(tarif)
        return tarif
    }

}

private fun Tarif.isNotOutdated(timeService: TimeService) =
        timeService.now() - this.timestamp < 24.hours()


fun Int.hours() = Timestamp(this.toLong() * 60 * 60 * 1000)

