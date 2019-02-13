package de.compeople.swn.time

import kotlin.js.Date

actual class TimeService actual constructor() {
    actual fun now(): Timestamp {
        val date = Date()
        return Timestamp(date.getTime().toLong())
    }
}