package de.compeople.swn

import android.app.Application
import de.compeople.swn.view.BaseActivity

class TarifApplication : Application() {

    val presenterProvider by lazy { PresenterProvider(this) }
}

val BaseActivity.presenterProvider: PresenterProvider
    get() = (application as TarifApplication).presenterProvider

