package de.compeople.swn.tarifService

import de.compeople.swn.data.Gender
import de.compeople.swn.data.User
import de.compeople.swn.time.TimeService
import de.compeople.swn.time.Timestamp

private const val YEAR_IN_MILLIS: Long = 365L * 24 * 60 * 60 * 1000

class Rechenkern(private val tarifService: CommonTarifService, private val timeService: TimeService) {

    suspend fun calculationPremium(user: User): Int {
        val tarif = tarifService.getTarif()
        if (tarif.tarifMinAge() > user.age()) {
            throw IllegalArgumentException("You must be 18 or old to get an insurance")
        } else {
            val tarifForAge = tarif.tarifForAge(user.age())
            return when (user.gender) {
                Gender.FEMALE -> tarifForAge.tarifFemale
                Gender.MALE -> tarifForAge.tarifMale
                else -> tarifForAge.tarifDivers
            }
        }
    }

    private fun User.age(): Int {
        return (timeService.now().year() - this.birthday.year)
    }
}

private fun Timestamp.year(): Int = 1970 + (this.millis / YEAR_IN_MILLIS).toInt()

