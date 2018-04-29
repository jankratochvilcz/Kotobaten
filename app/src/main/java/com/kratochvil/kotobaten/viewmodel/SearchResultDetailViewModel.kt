package com.kratochvil.kotobaten.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.os.Bundle
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
            notifyPropertyChanged(BR.favorited)
            notifyPropertyChanged(BR.canBeAutoFavorited)
            notifyPropertyChanged(BR.canBeManuallyFavorited)
        }

    var isFavorited: Boolean = false
        @Bindable get() = searchResult.isFavorited

    var canBeAutoFavorited: Boolean = false
        @Bindable get () = !isFavorited && searchResult.visitsCount < SearchResult.AUTOFAVORITE_THRESHOLD

    var canBeManuallyFavorited: Boolean = false
        @Bindable get () = !isFavorited && searchResult.visitsCount >= SearchResult.AUTOFAVORITE_THRESHOLD

    fun initialize(activationArgs: Bundle) {

        this.searchResult = navigationService.getSearchResultFromBundle(activationArgs)

        var updatedSearchResult = searchResultsRepository.onSearchResultVisited(searchResult)
        if(updatedSearchResult.visitsCount == 3 && !updatedSearchResult.isFavorited) {
            updatedSearchResult = searchResultsRepository.toggleIsFavorited(updatedSearchResult)
        }
        this.searchResult = updatedSearchResult
        this.historyPercentage = updatedSearchResult.visitsCount * 100 / SearchResult.AUTOFAVORITE_THRESHOLD
    }

    fun toggleFavorite() {
        searchResult = searchResultsRepository.toggleIsFavorited(searchResult)
    }
}