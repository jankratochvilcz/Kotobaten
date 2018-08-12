package com.kratochvil.kotobaten.view.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle

class OnboardingHelpFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)

        builder.setMessage(
                "Search for any word in English or Japanese by typing it into the search field and pressing the find icon on your keyboard. \n \n" +
                "When you look up the same word three times, we'll automatically favorite it for you. \n \n" +
                "Favorited words can later help you decide which words are worth learning. You can always find them in History. \n \n"
        )

        return builder.create()
    }
}