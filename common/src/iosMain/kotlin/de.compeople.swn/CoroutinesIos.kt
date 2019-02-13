package de.compeople.swn

import kotlinx.coroutines.runBlocking

actual fun <T> coRun(block: suspend () -> T) {
    runBlocking { block() }
}
