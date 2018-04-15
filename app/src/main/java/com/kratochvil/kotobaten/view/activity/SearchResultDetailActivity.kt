package com.kratochvil.kotobaten.view.activity

import android.databinding.DataBindingUtil
import android.databinding.Observable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.R

import com.kratochvil.kotobaten.databinding.ActivitySearchResultDetailBinding
import com.kratochvil.kotobaten.model.service.realm.RealmSearchResultsRepository
import com.kratochvil.kotobaten.view.services.PageNavigationService
import com.kratochvil.kotobaten.viewmodel.SearchResultDetailViewModel
import com.kratochvil.kotobaten.viewmodel.infrastructure.SearchResultDetailAdapter
import kotlinx.android.synthetic.main.activity_search_result_detail.*

class SearchResultDetailActivity : AppCompatActivity() {

    val navigationService = PageNavigationService(
            { this },
            { startActivity(it) }
    )

    private val viewModel = SearchResultDetailViewModel(
            RealmSearchResultsRepository()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent?.extras != null) {
            viewModel.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
                override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                    onViewModelPropertyChanged(propertyId)
                }
            })


            val binding = DataBindingUtil.setContentView<ActivitySearchResultDetailBinding>(
                    this,
                    R.layout.activity_search_result_detail)

            binding.viewModel = viewModel

            viewModel.initialize(navigationService.getSearchResultFromBundle(intent.extras))
        } else finish()
    }

    private fun onViewModelPropertyChanged(propertyId: Int) {
        if(propertyId == BR.searchResult)
            search_result_english_meanings.adapter = SearchResultDetailAdapter(this, viewModel.searchResult.definitions)
        else if(propertyId == BR.historyPercentage)
            search_result_autofavorite_progress.setProgressWithAnimation(viewModel.historyPercentage.toFloat())
    }
}