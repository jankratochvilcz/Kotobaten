package com.kratochvil.kotobaten.view.activity.view.services

import android.view.View
import android.view.inputmethod.InputMethodManager
import com.kratochvil.kotobaten.model.service.KeyboardService

class VirtualKeyboardService(
        private val viewFactory: () -> View?,
        private val inputMethodManagerFactory: () -> InputMethodManager?,
        private val showKeyboardFunc: () -> Unit)
    : KeyboardService {
    override fun showKeyboard() {
        showKeyboardFunc()
    }

    override fun hideKeyboard() {
        val inputMethodManager = inputMethodManagerFactory() ?: return
        val view = viewFactory() ?: return

        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}