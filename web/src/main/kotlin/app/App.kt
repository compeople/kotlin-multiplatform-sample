package app

import de.compeople.swn.data.User
import displayresults.displayResults
import react.*
import userinput.userInput


fun RBuilder.app() = child(App::class) {}

interface AppProps : RProps

interface AppState : RState {
    var activeView: App.ActiveView
    var user: User
    var premium: Int
}


class App : RComponent<AppProps, AppState>() {

    private val presenter = PresenterProvider()

    override fun AppState.init() {
        activeView = ActiveView.UserInput
        premium = 0
    }

    override fun RBuilder.render() {
        when (state.activeView) {
            ActiveView.UserInput -> userInput(presenter, state.user, ::showResult)
            ActiveView.Result -> displayResults(presenter, state.premium, ::showInput)
        }
    }

    private fun showResult(user: User, result: Int) {
        setState {
            this.user = user
            this.premium = result
            this.activeView = ActiveView.Result
        }
    }

    private fun showInput() {
        setState {
            this.activeView = ActiveView.UserInput
        }
    }

    enum class ActiveView {
        UserInput, Result
    }
}
