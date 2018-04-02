package com.kratochvil.kotobaten.view.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.ActivitySearchResultDetailBinding
import com.kratochvil.kotobaten.view.activity.view.services.PageNavigationService
import com.kratochvil.kotobaten.viewmodel.SearchResultDetailViewModel

class SearchResultDetailActivity : AppCompatActivity() {

    val navigationService = PageNavigationService(
            { this },
            { startActivity(it) }
    )

    private val viewModel = SearchResultDetailViewModel(
            navigationService)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(intent?.extras != null) {
            viewModel.searchResult = navigationService.getSearchResultFromBundle(intent.extras)

            val binding = DataBindingUtil.setContentView<ActivitySearchResultDetailBinding>(
                    this,
                    R.layout.activity_search_result_detail)

            binding.viewModel = viewModel
        } else finish()
    }
}