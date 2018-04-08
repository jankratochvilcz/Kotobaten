package com.kratochvil.kotobaten.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main)

        registerUiListeners()
    }

    private fun registerUiListeners() {
        activity_main_navigation.setNavigationItemSelectedListener { selectedMenuItem ->
            selectedMenuItem.isChecked = true
            activity_main_drawer.closeDrawers()

            true
        }
    }
}
