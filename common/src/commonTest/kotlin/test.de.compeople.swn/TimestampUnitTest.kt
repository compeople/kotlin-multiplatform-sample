package test.de.compeople.swn

import de.compeople.swn.time.Timestamp
import kotlin.js.JsName
import kotlin.test.*

class TimestampUnitTest {

    private val timestamp1 = Timestamp(300)
    private val timestamp2 = Timestamp(100)

    @Test
    @JsName("correctlyCompareTwoTimestamps")
    fun `check if two timestamp are compared correclty`() {
        val timestampCompareTo: Int = timestamp1.compareTo(timestamp2)
        assertEquals(1, timestampCompareTo)
        assertNotEquals(0, timestampCompareTo)
    }


    @Test
    @JsName("correctlyAddTwoTimestamps")
    fun `check if two timestamp are add correclty`() {
        val timestampAdd = timestamp1 + timestamp2
        assertEquals(Timestamp(400), timestampAdd)
    }


    @Test
    @JsName("correctlySubtractTwoTimestamps")
    fun `check if two timestamp are subtract correclty`() {
        val timestampMinus = timestamp1 - timestamp2
        assertEquals(Timestamp(200), timestampMinus)

        //TODO ist es sinnvoll einen negativen Timestamp erzeugen zu können?
        val timestampMinus2 = timestamp2 - timestamp1
        assertEquals(Timestamp(-200), timestampMinus2)
    }


}
