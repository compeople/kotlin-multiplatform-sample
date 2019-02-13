package app

import de.compeople.swn.presentation.BaseView
import react.RComponent
import react.RProps
import react.RState
import kotlin.browser.window

abstract class BaseComponent<P : RProps, S : RState>(props: P) : RComponent<P, S>(props), BaseView {


    override fun logError(error: Throwable) {
        console.error(error)
    }

    override fun showError(error: Throwable) {
        window.alert("Error: ${error.message}")
    }
}
