package de.compeople.swn.view

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import de.compeople.swn.R
import de.compeople.swn.presentation.displayResults.DisplayResultsView
import kotlinx.android.synthetic.main.activity_display_results.*
import java.util.*

class DisplayResultsActivity : BaseActivity(), DisplayResultsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_results)
        val message = intent.getStringExtra("monthlyCost")
        createView(message)

    }

    override fun logError(error: Throwable) {
        Log.e("LOG", error.message)
    }

    override fun showError(error: Throwable) =
            Toast.makeText(applicationContext, error.message, Toast.LENGTH_SHORT).show()


    private fun createView(message: String) {
        val systemLanguage = Locale.getDefault().language
        if (systemLanguage == "en") {
            textView2.text = "$message $"
        } else {
            textView2.text = "$message €"
        }
    }

}
