package de.compeople.swn.time

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

actual class TimeService actual constructor() {
    actual fun now(): Timestamp = Timestamp(NSDate().timeIntervalSince1970.toLong() * 1000)
}
