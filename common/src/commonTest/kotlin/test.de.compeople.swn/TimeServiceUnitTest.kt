package test.de.compeople.swn

import de.compeople.swn.coRun
import de.compeople.swn.time.TimeService
import de.compeople.swn.time.Timestamp
import kotlinx.coroutines.delay
import kotlin.test.Test
import kotlin.test.assertTrue

class TimeServiceUnitTest {

    private val timeService = TimeService()

    @Test
    fun testFunctionNow() = coRun {
        val time = timeService.now()
        delay(1000)
        val timeNow = timeService.now()
        assertTrue { time + 1000.ms <= timeNow }
    }

    private val Int.ms
        get() = Timestamp(this.toLong())
}