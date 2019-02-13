package de.compeople.swn.presentation.userinput

import de.compeople.swn.presentation.BaseView


interface UserInputView : BaseView {

    fun showInsurancePremium(monthlyCost: Int)

}


enum class UserInputValidator {
    FIRSTNAME, SURNAME, SUCCESS
}
