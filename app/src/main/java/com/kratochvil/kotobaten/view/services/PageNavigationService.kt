package com.kratochvil.kotobaten.view.services

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.widget.DrawerLayout
import android.view.Gravity
import com.kratochvil.kotobaten.R.id.activity_main_drawer
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.NavigationService
import com.kratochvil.kotobaten.view.activity.SearchResultActivity

class PageNavigationService(
        private val contextFunc: () -> Context,
        private val startActivityFunc: (intent: Intent) -> Unit,
        private val finishActivity: () -> Unit,
        private val activityFunc: () -> Activity)
    : NavigationService {
    override fun goBack() {
        finishActivity()
    }

    override fun openNavigationDrawer() {
        activityFunc()
                .findViewById<DrawerLayout>(activity_main_drawer)
                .openDrawer(Gravity.LEFT, true)
    }

    private val defaultBundleKey = "value"

    override fun navigateToSearchResultDetail(data: SearchResult) {
        val bundle = Bundle()
        bundle.putParcelable(defaultBundleKey, data)

        val intent = Intent(contextFunc(), SearchResultActivity::class.java)
        intent.putExtras(bundle)

        startActivityFunc(intent)
    }

    override fun getSearchResultFromBundle(bundle: Bundle): SearchResult {
        return bundle.getParcelable(defaultBundleKey)
    }
}