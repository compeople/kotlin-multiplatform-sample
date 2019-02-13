package test.de.compeople.swn.tarifService

import de.compeople.swn.tarifService.*
import de.compeople.swn.time.Timestamp
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.js.JsName
import kotlin.test.*

class TarifRepositoryTest {


    @MockK
    lateinit var keyValueStore: KeyValueStore

    @InjectMockKs
    lateinit var repository: TarifRepository

    private val TARIF_KEY = "Tarif"

    private val tarif18 = Tarif(listOf(TarifEntry(18, 170, 180, 175)), Timestamp(0))
    private val tarif40 = Tarif(listOf(TarifEntry(40, 470, 480, 475)), Timestamp(0))

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @JsName("getTarif_rst")
    fun `check that getTarif() delegates to KeyValueStore getValue()`() {
        // setup KeyValueStore
        every { keyValueStore.getValue(TARIF_KEY) } returnsMany listOf(tarif18.toJson(), tarif40.toJson(), null)

        assertEquals(tarif18, repository.getTarif())
        assertEquals(tarif40, repository.getTarif())
        assertNull(repository.getTarif())
    }

    @Test
    @JsName("setTarif")
    fun `check that setTarif() delegates to KeyValueStore setValue()`() {
        repository.setTarif(tarif18)

        verify { keyValueStore.setValue(TARIF_KEY, tarif18.toJson()) }

        repository.setTarif(tarif40)

        verify { keyValueStore.setValue(TARIF_KEY, tarif40.toJson()) }
    }
}