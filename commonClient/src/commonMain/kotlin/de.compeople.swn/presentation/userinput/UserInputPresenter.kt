package de.compeople.swn.presentation.userinput

import de.compeople.swn.ApplicationDispatcher
import de.compeople.swn.data.User
import de.compeople.swn.presentation.Presenter
import de.compeople.swn.tarifService.Rechenkern
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserInputPresenter(private val view: UserInputView, private val rechenkern: Rechenkern) :
        Presenter {

    fun calculateInsurancePremium(user: User) =
            GlobalScope.launch(ApplicationDispatcher) {
                coCalculateInsurancePremium(user)
            }

    internal suspend fun coCalculateInsurancePremium(user: User) {
        try {
            val premium = rechenkern.calculationPremium(user)
            view.showInsurancePremium(premium)
        } catch (e: Throwable) {
            view.showError(e.deepCause())
        }
    }

}

fun Throwable.deepCause(): Throwable =
        if (this.cause != null) {
            this.cause!!.deepCause()
        } else {
            this
        }
