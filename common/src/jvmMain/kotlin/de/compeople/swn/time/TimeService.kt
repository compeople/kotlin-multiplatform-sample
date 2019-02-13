package de.compeople.swn.time

actual class TimeService actual constructor() {
    actual fun now(): Timestamp = Timestamp(System.currentTimeMillis())

}