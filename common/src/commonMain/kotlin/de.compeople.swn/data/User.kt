package de.compeople.swn.data

data class User(val firstName: String = "not set", val surname: String = "not set", val gender: Gender = Gender.FEMALE,
                val birthday: Birthday = Birthday())
