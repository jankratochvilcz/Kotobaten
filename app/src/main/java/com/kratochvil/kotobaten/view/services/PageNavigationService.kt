package com.kratochvil.kotobaten.view.services

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.NavigationService
import com.kratochvil.kotobaten.view.activity.SearchResultDetailActivity

class PageNavigationService(
        private val contextFunc: () -> Context,
        private val startActivityFunc: (intent: Intent) -> Unit)
    : NavigationService {

    private val defaultBundleKey = "value"

    override fun navigateToSearchResultDetail(data: SearchResult) {
        val bundle = Bundle()
        bundle.putParcelable(defaultBundleKey, data)

        val intent = Intent(contextFunc(), SearchResultDetailActivity::class.java)
        intent.putExtras(bundle)

        startActivityFunc(intent)
    }

    override fun getSearchResultFromBundle(bundle: Bundle): SearchResult {
        return bundle.getParcelable(defaultBundleKey)
    }
}