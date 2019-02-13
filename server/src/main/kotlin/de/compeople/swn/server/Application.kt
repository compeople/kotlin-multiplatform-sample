package de.compeople.swn.server

import de.compeople.swn.server.tarif.tarif
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CORS
import io.ktor.routing.routing
import java.time.Duration

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    install(CORS) {
        anyHost()
        maxAge = Duration.ofDays(1)
    }

    routing {
        tarif()
    }

}


