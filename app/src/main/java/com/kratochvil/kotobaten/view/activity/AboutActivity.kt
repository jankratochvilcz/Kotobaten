package com.kratochvil.kotobaten.view.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import com.kratochvil.kotobaten.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        configureActionBar()
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun configureActionBar() {
        setSupportActionBar(about_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }
}