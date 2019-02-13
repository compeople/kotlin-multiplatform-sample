package de.compeople.swn.presentation.displayResults

import de.compeople.swn.presentation.Presenter

class DisplayResultsPresenter(private val displayResultsView: DisplayResultsView) : Presenter {

    fun navigateBack() {
        displayResultsView.goBackToUserInputView()
    }

}