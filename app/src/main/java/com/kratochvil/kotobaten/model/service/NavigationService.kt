package com.kratochvil.kotobaten.model.service

import android.os.Bundle
import com.kratochvil.kotobaten.model.entity.SearchResult

interface NavigationService {
    fun navigateToSearchResultDetail(data: SearchResult)

    fun getSearchResultFromBundle(bundle: Bundle): SearchResult
}