package de.compeople.swn.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import de.compeople.swn.presentation.BaseView
import de.compeople.swn.presentation.Presenter

abstract class BaseActivity : AppCompatActivity(), BaseView {

    //protected fun <T : Presenter> presenter(init: () -> T) = init
    private var Presenters: List<Presenter> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Presenters.forEach { it.onCreate() }
    }

    override fun onDestroy() {
        super.onDestroy()
        Presenters.forEach { it.onDestroy() }
    }

    override fun logError(error: Throwable) {
        Log.e("LOG", error.message)
    }

    override fun showError(error: Throwable) {
        logError(error)
        Toast.makeText(applicationContext, "Error ${error.message}", Toast.LENGTH_SHORT).show()
    }


}