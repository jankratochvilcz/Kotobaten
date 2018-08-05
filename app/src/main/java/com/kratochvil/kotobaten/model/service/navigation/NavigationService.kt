package com.kratochvil.kotobaten.model.service.navigation

import android.os.Bundle
import com.kratochvil.kotobaten.model.entity.SearchResult

interface NavigationService {
    var currentActivity: KotobatenActivity

    fun addNavigatedListener(listener: NavigatedListener)

    fun navigateToSearchResultDetail(data: SearchResult)

    fun getSearchResultFromBundle(bundle: Bundle): SearchResult

    fun openNavigationDrawer()

    fun goBack()
}