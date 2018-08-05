package com.kratochvil.kotobaten.view.activity

import android.app.Fragment
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.view.MenuItem
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.R.id.*
import com.kratochvil.kotobaten.databinding.ActivityMainBinding
import com.kratochvil.kotobaten.model.service.injection.InjectionParams
import com.kratochvil.kotobaten.model.service.navigation.KotobatenActivity
import com.kratochvil.kotobaten.view.fragment.HistoryFragment
import com.kratochvil.kotobaten.view.fragment.SearchFragment
import com.kratochvil.kotobaten.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject


class MainActivity : ActivityBase() {
    private val searchFragmentTag = "searchFragment"
    private val historyFragmentTag = "historyFragment"

    val viewModel by inject<MainViewModel> { mapOf(
            InjectionParams.GET_CURRENT_ACTIVITY_FUN to { this }
    ) }


    private val searchFragment: SearchFragment by lazy {
        SearchFragment()
    }

    private val historyFragment: HistoryFragment by lazy {
        HistoryFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main)

        registerUiListeners()

        viewModel.initialize()

        viewModel.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(observable: Observable, propertyId: Int) {
                if(propertyId == BR.currentActivity) {
                    val menuItemToCheck = when(viewModel.currentActivity) {
                        KotobatenActivity.SEARCH -> activity_main_navigation.menu.getItem(0)
                        KotobatenActivity.HISTORY ->  activity_main_navigation.menu.getItem(1)
                        KotobatenActivity.ABOUT ->  activity_main_navigation.menu.getItem(2)
                        KotobatenActivity.UNKNOWN -> null
                    }

                    if(menuItemToCheck != null)
                        menuItemToCheck.isChecked = true

                    activity_main_drawer.closeDrawers()
                }
            }
        })

        navigateToFragment(searchFragmentTag)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            activity_main_drawer.openDrawer(GravityCompat.START)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun registerUiListeners() {
        activity_main_navigation.setNavigationItemSelectedListener { selectedMenuItem ->
            when (selectedMenuItem.itemId) {
                drawer_main_search -> navigateToFragment(searchFragmentTag)
                drawer_main_history -> navigateToFragment(historyFragmentTag)
                drawer_main_about -> {
                    val intent = Intent(this, AboutActivity::class.java)
                    startActivity(intent)
                }
                else -> throw IllegalArgumentException()
            }

            true
        }
    }

    private fun navigateToFragment(fragmentTag: String) {
        val fragmentTransaction = fragmentManager.beginTransaction()
//        fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)

        val fragment = getFragment(fragmentTag)

        fragmentTransaction.replace(activity_main_content.id, fragment, fragmentTag)
        fragmentTransaction.addToBackStack(null)

        fragmentTransaction.commit()
        fragmentManager.executePendingTransactions()
    }

    private fun getFragment(tag: String): Fragment {
        return when(tag) {
            searchFragmentTag -> searchFragment
            historyFragmentTag -> historyFragment
            else -> throw IllegalArgumentException()
        }
    }
}
