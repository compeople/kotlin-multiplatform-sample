package de.compeople.swn.view

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import de.compeople.swn.R
import de.compeople.swn.data.Birthday
import de.compeople.swn.data.Gender
import de.compeople.swn.data.User
import de.compeople.swn.presentation.userinput.UserInputPresenter
import de.compeople.swn.presentation.userinput.UserInputValidator
import de.compeople.swn.presentation.userinput.UserInputView
import de.compeople.swn.presenterProvider
import kotlinx.android.synthetic.main.activity_userinput.*


class UserInputActivity : BaseActivity(), UserInputView {

    lateinit var presenter: UserInputPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = presenterProvider.userInputPresenter(this)

        setContentView(R.layout.activity_userinput)
        progressBar.visibility = View.GONE
        radioBtn_female.isChecked = true

        btn_continue.setOnClickListener {
            progressBar.visibility = View.VISIBLE
            sentFilledData()

        }
    }

    override fun onPause() {
        super.onPause()
        progressBar.visibility = View.GONE
    }

    override fun showInsurancePremium(monthlyCost: Int) {
        val intent = Intent(this, DisplayResultsActivity::class.java).apply { putExtra("monthlyCost", monthlyCost.toString()) }
        startActivity(intent)
    }

    private fun sentFilledData() {
        when (validateInput()) {
            UserInputValidator.FIRSTNAME -> showError(Throwable("Please enter your firstname"))
            UserInputValidator.SURNAME -> showError(Throwable("Please enter your surname"))
            UserInputValidator.SUCCESS -> {
                val user = User(editText_firstname.text.toString(), editText_surname.text.toString(), genderSelect(),
                        Birthday(datePicker_Birthday.dayOfMonth, datePicker_Birthday.month, datePicker_Birthday.year))
                presenter.calculateInsurancePremium(user) //false positive error: https://youtrack.jetbrains.com/issue/KT-26535

            }
            else -> showError(Throwable("unknown Error"))
        }
    }

    override fun logError(error: Throwable) {
        Log.e("LOG", error.message)
    }

    override fun showError(error: Throwable) {
        val alertDialog = AlertDialog.Builder(this@UserInputActivity).create()
        alertDialog.setTitle("Error")
        alertDialog.setMessage(error.message)
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "OK"
        ) { dialog, _ -> dialog.dismiss() }
        alertDialog.show()
    }

    private fun validateInput(): UserInputValidator = when {
        editText_firstname.text.isEmpty() -> UserInputValidator.FIRSTNAME
        editText_surname.text.isEmpty() -> UserInputValidator.SURNAME
        else -> UserInputValidator.SUCCESS
    }

    private fun genderSelect() = when {
        radioBtn_female.isChecked -> Gender.FEMALE
        radioBtn_male.isChecked -> Gender.MALE
        else -> Gender.DIVERS
    }

}

