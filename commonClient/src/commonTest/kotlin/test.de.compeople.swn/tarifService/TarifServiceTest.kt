package test.de.compeople.swn.tarifService

import de.compeople.swn.time.TimeService
import de.compeople.swn.tarifService.*
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import de.compeople.swn.coRun
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class TarifServiceTest {

    @MockK
    lateinit var tarifRepository: TarifRepository

    @MockK
    lateinit var tarifClient: TarifClient

    @MockK
    lateinit var timeService: TimeService

    @InjectMockKs
    lateinit var tarifService: TarifService

    private val tarif = Tarif(listOf(TarifEntry(18, 170, 180, 175)), 1.hours())
    private val tarifOutdated = Tarif(listOf(TarifEntry(18, 170, 180, 175)), 0.hours())

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)

        every { timeService.now() } returns 24.hours()

    }

    @Test
    @JsName("getTarifFromProviderIfNotOnDevice")
    fun `check if tarif is fetched from provider if it is not stored on the device`() = coRun {
        // setup mocks

        // every { tarifRepository.getTarif() } returns null  // <- nicht nötig, Standardverhalten des Mock
        coEvery { tarifClient.getTarif() } returns tarif

        // call service and check result
        assertEquals(tarif, tarifService.getTarif())

        // check if tarif has been stored in repository
        coVerify { tarifRepository.setTarif(tarif) }
    }

    @Test
    @JsName("getTarifFromProviderIfOnDeviceButOutdated")
    fun `check if tarif is fetched from provider if stored tarif is outdated`() = coRun {
        // setup mocks
        every { tarifRepository.getTarif() } returns tarifOutdated
        coEvery { tarifClient.getTarif() } returns tarif

        // call service and check result
        assertEquals(tarif, tarifService.getTarif())

        // check if tarif has been stored in repository
        coVerify { tarifRepository.setTarif(tarif) }
        // double check that server has been called
        coVerify { tarifClient.getTarif() }
    }


    @Test
    @JsName("getTarifFromDeviceIfPresentAndNotOutdated")
    fun `check that the stored tarif is used if it is present and not outdated`() = coRun {
        // setup mocks
        every { tarifRepository.getTarif() } returns tarif

        // call service and check result
        assertEquals(tarif, tarifService.getTarif())

        // double check that server has NOT been called
        coVerify(exactly = 0) { tarifClient.getTarif() }
    }

}