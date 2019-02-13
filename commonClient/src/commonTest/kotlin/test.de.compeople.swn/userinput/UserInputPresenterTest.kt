package test.de.compeople.swn.userinput

import de.compeople.swn.data.Birthday
import de.compeople.swn.data.Gender
import de.compeople.swn.data.User
import de.compeople.swn.presentation.userinput.UserInputPresenter
import de.compeople.swn.presentation.userinput.UserInputView
import de.compeople.swn.tarifService.*
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import de.compeople.swn.coRun
import io.mockk.coVerify
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test

class UserInputPresenterTest {

    @MockK
    lateinit var rechenkern: Rechenkern
    @MockK
    lateinit var view: UserInputView
    @InjectMockKs
    lateinit var presenter: UserInputPresenter

    private val user1 = User(surname = "user", firstName = "1", gender = Gender.FEMALE, birthday = Birthday(13, 3, 1969))
    private val user2 = User(surname = "user", firstName = "2", gender = Gender.MALE, birthday = Birthday(27, 2, 1974))
    private val user3 = User(surname = "user", firstName = "3", gender = Gender.DIVERS, birthday = Birthday(22, 2, 2002))
    private val error = Throwable()

    @BeforeTest
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    @JsName("coCalculateInsurancePremiumCallsRechenkern")
    fun `check if rechenkern is called`() = coRun {
        //check if presenter calls rechenkern
        presenter.coCalculateInsurancePremium(user1)
        //check if rechenkern was correctly called
        coVerify { rechenkern.calculationPremium(user1) }

        //check if presenter calls rechenkern
        presenter.coCalculateInsurancePremium(user2)
        //check if rechenkern was correctly called
        coVerify { rechenkern.calculationPremium(user2) }

    }

    @Test
    @JsName("coCalculateInsurancePremiumUpdatesView")
    fun `check if presenter updates view correctly`() = coRun {
        coEvery { rechenkern.calculationPremium(user1) } returns 100
        coEvery { rechenkern.calculationPremium(user2) } returns 500

        presenter.coCalculateInsurancePremium(user1)
        verify { view.showInsurancePremium(100) }

        presenter.coCalculateInsurancePremium(user2)
        verify { view.showInsurancePremium(500) }
    }

    @Test
    @JsName("checkExceptionHandling")
    fun `check if any exception is catchted and shown to user`() = coRun {
        coEvery { rechenkern.calculationPremium(user3) } throws error

        presenter.coCalculateInsurancePremium(user3)
        verify { view.showError(error) }

    }

}