package com.kratochvil.kotobaten.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.ActivityAboutBinding
import com.kratochvil.kotobaten.databinding.ActivitySearchResultBinding
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : ActivityBase() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityAboutBinding>(
                this,
                R.layout.activity_about)

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