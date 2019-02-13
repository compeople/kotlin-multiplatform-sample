package test.de.compeople.swn.tarifService

import de.compeople.swn.coRun
import de.compeople.swn.tarifService.*
import de.compeople.swn.time.Timestamp
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.MockHttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import kotlinx.coroutines.io.ByteReadChannel
import kotlinx.io.charsets.Charsets
import kotlinx.io.core.toByteArray
import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertEquals

class TarifClientTest {


    private val tarif18 = Tarif(listOf(TarifEntry(18, 170, 180, 175)), Timestamp(0))
    private val tarif40 = Tarif(listOf(TarifEntry(40, 470, 480, 475)), Timestamp(0))

    @Test
    @JsName("getTarif")
    fun `check that getTarif() calls server`() = coRun {

        val mockHttpClient = mockHttpClient(tarif18, tarif40)
        val tarifProvider = TarifClient(mockHttpClient)

        assertEquals(tarif18, tarifProvider.getTarif())
        assertEquals(tarif40, tarifProvider.getTarif())
    }


    private fun mockHttpClient(vararg tarife: Tarif): HttpClient {
        val answers = tarife.iterator()

        val httpMockEngine = MockEngine { ->
            // this: HttpRequest, call: HttpClientCall
            // TODO(insert your local server ip here)
            if (url.toString() == "http://localhost:8080/tarif" &&
                    method == HttpMethod.Get) {
                MockHttpResponse(
                        call,
                        HttpStatusCode.OK,
                        ByteReadChannel(answers.next().toJson().toByteArray(Charsets.UTF_8)),
                        headersOf("Content-Type" to listOf(ContentType.Application.Json.toString()))
                )
            } else {
                error("Unhandled $url")
            }
        }
        return HttpClient(httpMockEngine)
    }
}