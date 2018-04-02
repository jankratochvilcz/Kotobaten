package com.kratochvil.kotobaten.view.activity.view.services

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
        var bundle = Bundle()
        bundle.putSerializable(defaultBundleKey, data)

        var intent = Intent(contextFunc(), SearchResultDetailActivity::class.java)
        intent.putExtras(bundle)

        startActivityFunc(intent)
    }

    override fun getSearchResultFromBundle(bundle: Bundle): SearchResult {
        val serializable = bundle.getSerializable(defaultBundleKey)
        return serializable as SearchResult
    }
}