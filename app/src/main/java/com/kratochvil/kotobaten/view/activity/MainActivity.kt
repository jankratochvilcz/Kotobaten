package com.kratochvil.kotobaten.view.activity

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.R.id.drawer_main_history
import com.kratochvil.kotobaten.R.id.drawer_main_search
import com.kratochvil.kotobaten.databinding.ActivityMainBinding
import com.kratochvil.kotobaten.view.fragment.HistoryFragment
import com.kratochvil.kotobaten.view.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private val searchFragmentTag = "searchFragment"
    private val historyFragmentTag = "historyFragment"

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

        navigateToFragment(searchFragmentTag)
    }

    private fun registerUiListeners() {
        activity_main_navigation.setNavigationItemSelectedListener { selectedMenuItem ->
            selectedMenuItem.isChecked = true
            activity_main_drawer.closeDrawers()

            val fragmentTag = when (selectedMenuItem.itemId) {
                drawer_main_search -> searchFragmentTag
                drawer_main_history -> historyFragmentTag
                else -> throw IllegalArgumentException()
            }

            navigateToFragment(fragmentTag)

            true
        }
    }

    private fun navigateToFragment(fragmentTag: String) {
        val fragmentTransaction = fragmentManager.beginTransaction()

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

    override fun onBackPressed() {
        super.onBackPressed()

        val currentFragment = activity_main_content.getChildAt(0)

//        val currentFragment = fragmentManager.findFragmentById(activity_main_content.id)
        val menuItemToCheckId = when (currentFragment.tag) {
            searchFragmentTag -> drawer_main_search
            historyFragmentTag -> drawer_main_history
            else -> throw IllegalArgumentException()
        }

        activity_main_navigation.setCheckedItem(menuItemToCheckId)
    }
}
