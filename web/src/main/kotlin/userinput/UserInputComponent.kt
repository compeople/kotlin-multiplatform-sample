package userinput

import app.BaseComponent
import app.PresenterProvider
import de.compeople.swn.data.Birthday
import de.compeople.swn.data.Gender
import de.compeople.swn.data.User
import de.compeople.swn.presentation.userinput.UserInputPresenter
import de.compeople.swn.presentation.userinput.UserInputValidator
import de.compeople.swn.presentation.userinput.UserInputView
import io.ktor.util.date.Month
import kotlinx.html.InputType
import kotlinx.html.id
import kotlinx.html.js.onChangeFunction
import kotlinx.html.js.onClickFunction
import kotlinx.html.role
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLSelectElement
import org.w3c.dom.events.Event
import react.*
import react.dom.*

typealias ShowResultHandler = (user: User, result: Int) -> Unit

interface UserInputProps : RProps {
    var presenterProvider: PresenterProvider
    var user: User?
    var showResultHandler: ShowResultHandler
}

interface UserInputState : RState {
    var firstname: String
    var surname: String
    var birthday: Birthday
    var gender: Gender
    var loading: Boolean
}

fun RBuilder.userInput(presenterProvider: PresenterProvider, user: User?, showResultHandler: ShowResultHandler) = child(UserInputComponent::class) {
    attrs.presenterProvider = presenterProvider
    attrs.user = user
    attrs.showResultHandler = showResultHandler
}

object empty : ReactElement {
    override val props = object : RProps {}
}


class UserInputComponent(props: UserInputProps) : BaseComponent<UserInputProps, UserInputState>(props), UserInputView {

    private lateinit var presenter: UserInputPresenter

    private val user: User
        get() = User(firstName = state.firstname, surname = state.surname, birthday = state.birthday, gender = state.gender)

    override fun UserInputState.init(props: UserInputProps) {
        presenter = props.presenterProvider.createUserInputPresenter(this@UserInputComponent)
        firstname = props.user?.firstName ?: ""
        surname = props.user?.surname ?: ""
        birthday = props.user?.birthday ?: Birthday()
        gender = props.user?.gender ?: Gender.FEMALE
        loading = false
    }

    override fun RBuilder.render() {
        div(classes = "App-header") {
            h2 {
                +"Welcome to your insurance premium calculator"
            }
            p {
                +"Note: This is only a first attempt to calculate your insurance, the premium can change at every time"
            }
        }
        div("userInputMaske") {
            textInput(state.firstname, "first name")
            textInput(state.surname, "surname")
            form(classes = "form-group") {
                label {
                    +"select your birthday:"
                }
                div(classes = "selection") {
                    select(classes = "form-control custom-select") {
                        attrs {
                            id = "day"
                            value = user.birthday.day.toString()
                            onChangeFunction = ::onDayChange
                        }
                        for (i in 1..31) {
                            option {
                                +"$i"
                            }
                        }
                    }
                    select(classes = "form-control custom-select-month") {
                        attrs {
                            id = "month"
                            value = Month.from(user.birthday.month - 1).toString().toLowerCase()
                            onChangeFunction = ::onMonthChange
                        }
                        Month.values().map {
                            option {
                                +it.toString().toLowerCase()
                            }
                        }
                    }

                    select(classes = "form-control custom-select-year") {
                        attrs {
                            id = "year"
                            value = user.birthday.year.toString()
                            onChangeFunction = ::onYearChange
                        }
                        for (i in 1953..2000) {
                            option {
                                +"$i"
                            }
                        }
                    }
                }
            }

            div(classes = "form-group") {
                label {
                    +"select your gender"
                }
                div(classes = "radio") {
                    attrs {
                        onChangeFunction = {
                            onGenderChecked(it)
                        }
                        radioOptions(Gender.FEMALE)
                        radioOptions(Gender.MALE)
                        radioOptions(Gender.DIVERS)
                    }
                }
            }
            div(classes = "form-group") {
                loading(state.loading)
            }
            button(classes = "btn btn-primary") {
                attrs {
                    onClickFunction = {
                        onLoadingChange(true)
                        calculateInsurancePremium()
                    }
                }
                +"calculate"
            }
        }
    }

    private fun calculateInsurancePremium() {
        when (validateInput()) {
            UserInputValidator.FIRSTNAME -> showError(Throwable("Please enter your first name"))
            UserInputValidator.SURNAME -> showError(Throwable("Please enter your surname"))
            else -> {
                presenter.calculateInsurancePremium(user)
            }
        }
    }

    override fun showInsurancePremium(monthlyCost: Int) {
        state.loading = false
        props.showResultHandler(user, monthlyCost)
    }

    private fun validateInput(): UserInputValidator {
        return if (user.firstName == "" || user.firstName == "not set") {
            UserInputValidator.FIRSTNAME
        } else if (user.surname == "" || user.surname == "not set") {
            UserInputValidator.SURNAME
        } else {
            UserInputValidator.SUCCESS
        }
    }

    private fun onLoadingChange(isLoading: Boolean) {
        setState {
            loading = isLoading
        }
    }


    private fun onFirstnameChange(event: Event) {
        val target = event.target as HTMLInputElement
        setState {
            firstname = target.value
        }
    }

    private fun onSurnameChange(event: Event) {
        val target = event.target as HTMLInputElement
        setState {
            surname = target.value
        }
    }

    private fun onDayChange(event: Event) {
        val target = event.target as HTMLSelectElement
        setState {
            birthday = birthday.withDay(target.value.toInt())
        }
    }

    private fun onMonthChange(event: Event) {
        val target = event.target as HTMLSelectElement
        setState {
            birthday = birthday.withMonth(target.selectedIndex + 1)
        }
    }

    private fun onYearChange(event: Event) {
        val target = event.target as HTMLSelectElement
        setState {
            birthday = birthday.withYear(target.value.toInt())
        }
    }

    private fun onGenderChecked(event: Event) {
        val target = event.target as HTMLInputElement
        if (target.checked && target.id == "female") {
            setState {
                gender = Gender.FEMALE
            }
        } else if (target.checked && target.id == "male") {
            setState {
                gender = Gender.MALE
            }
        } else {
            setState {
                gender = Gender.DIVERS
            }
        }
    }

    private fun RBuilder.radioOptions(gender: Gender) {
        div(classes = "radio") {
            label {
                input(InputType.radio, name = "genderSelection") {
                    attrs {
                        id = gender.toString().toLowerCase()
                        checked = user.gender == gender
                    }
                }
                +gender.toString().toLowerCase()
            }
        }
    }

    private fun RBuilder.textInput(nameType: String, placeholderText: String) {
        div("form-group") {
            label(classes = "textLabel") {
                +placeholderText
            }
            input(InputType.text, classes = "form-control") {
                attrs {
                    value = nameType
                    onChangeFunction = {
                        validateInputText(it, "[0-9]".toRegex(), "no numbers allowed")
                        when (nameType) {
                            state.firstname -> onFirstnameChange(it)
                            else -> onSurnameChange(it)
                        }

                    }
                    placeholder = placeholderText
                }
            }
        }
    }

    private fun RBuilder.loading(isLoading: Boolean) = if (isLoading) {
            spinner()
    } else {
        empty
    }

    private fun RBuilder.spinner() = div("spinner") {}

    private fun validateInputText(event: Event, regex: Regex, errorMessage: String) {
        val target = event.target as HTMLInputElement
        if (target.value.contains(regex)) {
            showError(Throwable(errorMessage))
            target.value = target.value.dropLast(1)
        }
    }

}

private fun Birthday.withDay(day: Int) = Birthday(day, this.month, this.year)
private fun Birthday.withMonth(month: Int) = Birthday(this.day, month, this.year)
private fun Birthday.withYear(year: Int) = Birthday(this.day, this.month, year)
