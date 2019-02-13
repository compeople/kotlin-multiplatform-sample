package test.de.compeople.swn.displayResults

import de.compeople.swn.presentation.displayResults.DisplayResultsPresenter
import de.compeople.swn.presentation.displayResults.DisplayResultsView
import io.mockk.*
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test

class DisplayResultsPresenterTest {

    @MockK
    lateinit var resultView: DisplayResultsView

    @InjectMockKs
    lateinit var resultPresenter: DisplayResultsPresenter


    @BeforeTest
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    @JsName("displayResultPresenterNavigatesBackToUserInputView")
    fun `check if result presenter navigates correctly back to input view`() {
        resultPresenter.navigateBack()

        verify { resultView.goBackToUserInputView() }
    }

}