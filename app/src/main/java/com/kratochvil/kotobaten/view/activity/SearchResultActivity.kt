package com.kratochvil.kotobaten.view.activity

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.ActivitySearchResultBinding
import com.kratochvil.kotobaten.model.service.injection.InjectionParams
import com.kratochvil.kotobaten.viewmodel.SearchResultDetailViewModel
import com.kratochvil.kotobaten.viewmodel.infrastructure.SearchResultDetailAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_search_result.*
import org.koin.android.ext.android.inject

class SearchResultActivity : AppCompatActivity() {
    private val viewModel by inject<SearchResultDetailViewModel> { mapOf(
            InjectionParams.GET_CURRENT_ACTIVITY_FUN to { this }
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent?.extras != null) {
            viewModel.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    onViewModelPropertyChanged(propertyId)
                }
            })


            val binding = DataBindingUtil.setContentView<ActivitySearchResultBinding>(
                    this,
                    R.layout.activity_search_result)

            configureActionBar()

            binding.viewModel = viewModel

            viewModel.initialize(intent.extras)

        } else finish()
    }

    private fun onViewModelPropertyChanged(propertyId: Int) {
        if(propertyId == BR.searchResult)
            search_result_english_meanings.adapter = SearchResultDetailAdapter(this, viewModel.searchResult.definitions)
        else if(propertyId == BR.historyPercentage)
            search_result_autofavorite_progress.setProgressWithAnimation(viewModel.historyPercentage.toFloat())
    }

    private fun configureActionBar() {
        setSupportActionBar(search_result_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_black_24dp)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home) {
            finish()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}