package de.compeople.swn.presentation


interface BaseView {

    fun logError(error: Throwable) {}
    fun showError(error: Throwable) {}
}