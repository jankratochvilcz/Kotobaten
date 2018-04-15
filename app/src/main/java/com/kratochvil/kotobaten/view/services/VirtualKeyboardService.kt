package com.kratochvil.kotobaten.view.services

import android.view.View
import android.view.inputmethod.InputMethodManager
import com.kratochvil.kotobaten.model.service.KeyboardService

class VirtualKeyboardService
    : KeyboardService {

    private var viewFactory: () -> View? = { null }
    private var inputMethodManagerFactory: () -> InputMethodManager? = { null }
    private var showKeyboardFunc: () -> Unit = { }

    fun initialize(
            viewFactory: () -> View?,
            inputMethodManagerFactory: () -> InputMethodManager?,
            showKeyboardFunc: () -> Unit) {
        this.viewFactory = viewFactory
        this.inputMethodManagerFactory = inputMethodManagerFactory
        this.showKeyboardFunc = showKeyboardFunc
    }

    override fun showKeyboard() {
        showKeyboardFunc()
    }

    override fun hideKeyboard() {
        val inputMethodManager = inputMethodManagerFactory() ?: return
        val view = viewFactory() ?: return

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}