package test.de.compeople.swn

import de.compeople.swn.tarifService.Tarif
import de.compeople.swn.tarifService.TarifEntry
import de.compeople.swn.time.Timestamp
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class TarifUnitTest {

    private val tarif18: TarifEntry = TarifEntry(18, 100, 120, 115)
    private val tarif40: TarifEntry = TarifEntry(40, 140, 150, 145)
    private val tarif67: TarifEntry = TarifEntry(67, 160, 170, 165)

    @Test
    @JsName("tarifForAge")
    fun `check if right tarifEntry if choose based on users age`() {
        val tarif = Tarif(listOf(tarif18, tarif40, tarif67), Timestamp(0))
        assertEquals(tarif18, tarif.tarifForAge(18))
        assertEquals(tarif18, tarif.tarifForAge(26))
        assertEquals(tarif18, tarif.tarifForAge(39))

        assertEquals(tarif40, tarif.tarifForAge(40))
        assertEquals(tarif40, tarif.tarifForAge(45))
        assertEquals(tarif40, tarif.tarifForAge(66))

        assertEquals(tarif67, tarif.tarifForAge(67))
        assertEquals(tarif67, tarif.tarifForAge(70))
        assertEquals(tarif67, tarif.tarifForAge(85))
    }

    @Test
    @JsName("tarifForAgeWithOnlyOneTarif")
    fun `check if right tarif is choosen based on age if only one tarifEntry`() {
        val tarif = Tarif(listOf(tarif18), Timestamp(0))
        assertEquals(tarif18, tarif.tarifForAge(18))
        assertEquals(tarif18, tarif.tarifForAge(39))
        assertEquals(tarif18, tarif.tarifForAge(64))
        assertEquals(tarif18, tarif.tarifForAge(84))
    }

    @Test //BirthdayChooser is configured that you can only choose from 31.12.2000 back
    @JsName("tarifForAgeLowerMinAge")
    fun `check if tarif correctly fails when user is not old enough for insurance`() {
        val tarif = Tarif(listOf(tarif18, tarif40, tarif67), Timestamp(0))
        assertFails { tarif.tarifForAge(17) }
        assertFails { tarif.tarifForAge(3) }

    }

    @Test
    @JsName("tarifMinAge")
    fun `check if right tarif is choose based on users age`() {
        val tarif = Tarif(listOf(tarif18, tarif40, tarif67), Timestamp(0))
        assertEquals(18, tarif.tarifMinAge())

        val tarif2 = Tarif(listOf(tarif40, tarif67), Timestamp(0))
        assertEquals(40, tarif2.tarifMinAge())
    }
}