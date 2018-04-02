package com.kratochvil.kotobaten.view.activity

import android.content.Context
import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.ActivityMainBinding
import com.kratochvil.kotobaten.view.activity.view.services.PageNavigationService
import com.kratochvil.kotobaten.view.activity.view.services.VirtualKeyboardService
import com.kratochvil.kotobaten.viewmodel.MainViewModel
import com.kratochvil.kotobaten.viewmodel.infrastructure.SearchResultAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel = MainViewModel(
            VirtualKeyboardService(
                { currentFocus },
                { getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager },
                {
                    main_search_edit_text.requestFocus()
                    val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.toggleSoftInputFromWindow(
                            container.applicationWindowToken,
                            InputMethodManager.SHOW_FORCED, 0)
                }),
            PageNavigationService(
                { this },
                { startActivity(it) }
            )
    )

    /*private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                onViewModelPropertyChanged(propertyId)
            }
        })

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(
                this,
                R.layout.activity_main)

        binding.viewModel = viewModel

        registerUiListeners()

//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun registerUiListeners() {
        main_search_edit_text.setOnEditorActionListener({ _, _, _ ->
            viewModel.search()
            true
        })

        search_results_list_view.setOnItemClickListener { parent, view, position, id ->
            viewModel.goToSearchResultDetail(viewModel.results[position])
        }
    }

    private fun onViewModelPropertyChanged(propertyId: Int) {
        if(propertyId == BR.results)
            search_results_list_view.adapter = SearchResultAdapter(this, viewModel.results)
    }
}
