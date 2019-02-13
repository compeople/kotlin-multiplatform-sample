package de.compeople.swn.time

import kotlinx.serialization.Serializable
import kotlin.math.sign

@Serializable
data class Timestamp(val millis: Long) {

    operator fun compareTo(other: Timestamp): Int = (this.millis - other.millis).sign

    operator fun minus(other: Timestamp): Timestamp = Timestamp(this.millis - other.millis)

    operator fun plus(other: Timestamp): Timestamp = Timestamp(this.millis + other.millis)

}