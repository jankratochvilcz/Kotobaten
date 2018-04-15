package com.kratochvil.kotobaten.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.NavigationService
import com.kratochvil.kotobaten.model.service.SearchResultsRepository

class SearchResultDetailViewModel(
        private val searchResultsRepository: SearchResultsRepository,
        private val navigationService: NavigationService
) : BaseObservable() {

    private var _searchResult = SearchResult()
    private var _historyPercentage = 0

    var historyPercentage: Int
        @Bindable get() = _historyPercentage
        private set (x) {
            _historyPercentage = if (x > 100) 100 else x
            notifyPropertyChanged(BR.historyPercentage)
        }

    var searchResult: SearchResult
        @Bindable get() = _searchResult
        private set (x) {
            _searchResult = x
            notifyPropertyChanged(BR.searchResult)
            notifyPropertyChanged(BR.historyPercentage)
        }

    var isFrequent: Boolean = false
        @Bindable get() = historyPercentage >= 100

    fun initialize(searchResult: SearchResult) {
        this.searchResult = searchResult

        val updatedSearchResult = searchResultsRepository.onSearchResultVisited(searchResult)

        this.searchResult = updatedSearchResult
        this.historyPercentage = updatedSearchResult.visitsCount * 100 / SearchResult.AUTOFAVORITE_THRESHOLD
    }

    fun goBack() {
        navigationService.goBack()
    }
}