package de.compeople.swn.server.tarif

import de.compeople.swn.tarifService.Tarif
import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.route
import kotlinx.serialization.json.Json

fun Routing.tarif() {

    route("tarif") {

        get {
            val tarif = TarifService.getTarif()
            call.respondText(Json.stringify(Tarif.serializer(), tarif), ContentType.Application.Json)
        }

    }
}
