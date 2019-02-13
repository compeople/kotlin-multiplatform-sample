package app

import de.compeople.swn.presentation.displayResults.DisplayResultsPresenter
import de.compeople.swn.presentation.displayResults.DisplayResultsView
import de.compeople.swn.presentation.userinput.UserInputPresenter
import de.compeople.swn.presentation.userinput.UserInputView
import de.compeople.swn.tarifService.Rechenkern
import de.compeople.swn.tarifService.TarifClient
import de.compeople.swn.tarifService.TarifRepository
import de.compeople.swn.tarifService.TarifService
import de.compeople.swn.time.TimeService

class PresenterProvider {

    private val timeService = TimeService()
    private val keyValueStore = KeyValueStoreWeb()
    private val tarifRepo = TarifRepository(keyValueStore)
    private val tarifService = TarifService(tarifRepo, TarifClient(), timeService)
    private val rechenkern = Rechenkern(tarifService, timeService)

    fun createUserInputPresenter(view: UserInputView): UserInputPresenter {
        return UserInputPresenter(view, rechenkern)
    }

    fun createDisplayResultsPresenter(view: DisplayResultsView): DisplayResultsPresenter {
        return DisplayResultsPresenter(view)
    }
}