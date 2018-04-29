package com.kratochvil.kotobaten.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.NavigationService
import com.kratochvil.kotobaten.model.service.SearchResultsRepository

class HistoryViewModel(
        private val searchResultsRepository: SearchResultsRepository,
        private val navigationService: NavigationService
): BaseObservable() {

    private var _allResults: List<SearchResult> = listOf()
    private var _showOnlyFavoriteResults = false

    private var allResults: List<SearchResult>
        @Bindable get() = _allResults
        set (x) {
            _allResults = x
            notifyPropertyChanged(BR.results)
            notifyPropertyChanged(BR.allResults)
        }

    var showOnlyFavoriteResults: Boolean
        @Bindable get() = _showOnlyFavoriteResults
        set (x) {
            _showOnlyFavoriteResults = x
            notifyPropertyChanged(BR.showOnlyFavoriteResults)
            notifyPropertyChanged(BR.results)
        }

    var results: List<SearchResult> = listOf()
        @Bindable get() = if (showOnlyFavoriteResults)
            allResults.filter { it.isFavorited }
        else
            allResults

    fun initialize() {
        allResults = searchResultsRepository.getVisitedSearchResults()
    }

    fun goToSearchResultDetail(searchResult: SearchResult) {
        navigationService.navigateToSearchResultDetail(searchResult)
    }
}