package de.compeople.swn.tarifService

import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.response.readText
import io.ktor.http.HttpMethod

class TarifClient(internal val httpClient: HttpClient) {

    // Swift doesn't like default parameters?!?
    constructor() : this(createHttpClient())

    // Implicit serialization currently not supported in native, see TODO
    //  KClass<T>.compiledSerializer() in kotlinx.serialization.Platform
    // https://github.com/Kotlin/kotlinx.serialization/blob/master/runtime/native/src/main/kotlin/kotlinx/serialization/Platform.kt
    suspend fun getTarif(): Tarif =
            //TODO(insert your local server ip here)
            httpClient.call("http://localhost:8080/tarif") {
                method = HttpMethod.Get
            }.response.readText().parseTarifJson()

}

fun createHttpClient() =
        HttpClient {
            //no error, its a known issue see https://youtrack.jetbrains.com/issue/KT-28922
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }
