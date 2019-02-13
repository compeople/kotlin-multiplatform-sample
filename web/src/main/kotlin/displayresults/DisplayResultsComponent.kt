package displayresults

import app.BaseComponent
import app.PresenterProvider
import de.compeople.swn.presentation.displayResults.DisplayResultsPresenter
import de.compeople.swn.presentation.displayResults.DisplayResultsView
import kotlinx.html.js.onClickFunction
import react.RBuilder
import react.RProps
import react.RState
import react.dom.*


typealias GoBackHandler = () -> Unit

interface DisplayResultsState : RState

interface DisplayResultsProps : RProps {
    var presenterProvider: PresenterProvider
    var goBackHandler: GoBackHandler
    var result: Int
}

class DisplayResultsComponent(props: DisplayResultsProps) : BaseComponent<DisplayResultsProps, DisplayResultsState>(props), DisplayResultsView {

    private var presenter: DisplayResultsPresenter

    init {
        presenter = props.presenterProvider.createDisplayResultsPresenter(this)
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
        div(classes = "form-group result") {
            div {
                label {
                    +"Result: ${props.result}"
                }
            }
            button(classes = "btn btn-primary") {
                attrs {
                    onClickFunction = {
                        onBack()
                    }
                }
                +"back"
            }
        }
    }

    private fun onBack() {
        presenter.navigateBack()
    }

    override fun goBackToUserInputView() {
        props.goBackHandler()
    }

}

fun RBuilder.displayResults(presenterProvider: PresenterProvider, result: Int, goBackHandler: GoBackHandler) = child(DisplayResultsComponent::class) {
    attrs.presenterProvider = presenterProvider
    attrs.result = result
    attrs.goBackHandler = goBackHandler
}
