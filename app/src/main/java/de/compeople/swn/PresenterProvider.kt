package de.compeople.swn

import android.app.Application
import android.preference.PreferenceManager
import de.compeople.swn.presentation.userinput.UserInputPresenter
import de.compeople.swn.tarifService.*
import de.compeople.swn.time.TimeService
import de.compeople.swn.view.UserInputActivity

class PresenterProvider(private val application: Application) {

    private val timeService = TimeService()
    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)
    private val keyValueStore: KeyValueStore = KeyValueStoreAndroid(sharedPreferences)
    private val tarifClient = TarifClient()
    private val tarifRepository = TarifRepository(keyValueStore)
    private val tarifService = TarifService(tarifRepository, tarifClient, timeService)
    private val rechenkern = Rechenkern(tarifService, timeService)

    fun userInputPresenter(activity: UserInputActivity): UserInputPresenter = UserInputPresenter(activity, rechenkern)

}
