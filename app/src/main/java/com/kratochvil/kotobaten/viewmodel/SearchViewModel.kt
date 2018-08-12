package com.kratochvil.kotobaten.viewmodel

import android.databinding.Bindable
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.KeyboardService
import com.kratochvil.kotobaten.model.service.navigation.KotobatenActivity
import com.kratochvil.kotobaten.model.service.navigation.NavigationService
import com.kratochvil.kotobaten.viewmodel.infrastructure.SearchTask

class SearchViewModel(
        private val keyboardService: KeyboardService,
        private val navigationService: NavigationService
) : ViewModelBase(navigationService) {

    private var _loadingResults = false
    private var _searchTerm = ""
    private var _results = listOf<SearchResult>()
    private var _isNoResultsMessageVisible = false

    var loadingResults: Boolean
        @Bindable get() = _loadingResults
        set(x) {
            _loadingResults = x

            notifyPropertyChanged(BR.loadingResults)
            notifyPropertyChanged(BR.clearSearchTermUiVisible)
            notifyPropertyChanged(BR.noResultsMessage)
            notifyPropertyChanged(BR.noResultsMessageVisible)
        }

    var searchTerm: String
        @Bindable get() = _searchTerm
        set (x) {
            if(_searchTerm == x)
                return

            _searchTerm = x
            isNoResultsMessageVisible = false

            notifyPropertyChanged(BR.searchTerm)
            notifyPropertyChanged(BR.clearSearchTermUiVisible)
            notifyPropertyChanged(BR.canSearch)
            notifyPropertyChanged(BR.noResultsMessage)
            notifyPropertyChanged(BR.noResultsMessageVisible)
        }

    var results: List<SearchResult>
    @Bindable get() = _results
    set (x) {
        _results = x

        if(!results.any())
            isNoResultsMessageVisible = true

        notifyPropertyChanged(BR.areSearchResultsVisible)
        notifyPropertyChanged(BR.emptyScreenUiVisible)
        notifyPropertyChanged(BR.results)
        notifyPropertyChanged(BR.noResultsMessage)
        notifyPropertyChanged(BR.noResultsMessageVisible)
    }

    var isClearSearchTermUiVisible: Boolean = false
        @Bindable get() = !loadingResults && searchTerm.any()

    var areSearchResultsVisible: Boolean = false
        @Bindable get() = results.any()

    var isEmptyScreenUiVisible: Boolean = true
        @Bindable get() = !results.any()

    var canSearch: Boolean = false
        @Bindable get() = searchTerm.any()

    var noResultsMessage: String = ""
        @Bindable get() = "No results for \"" + searchTerm + "\""

    var isNoResultsMessageVisible: Boolean
        @Bindable get() = _isNoResultsMessageVisible
        set (x) {
            if(_isNoResultsMessageVisible == x)
                return

            _isNoResultsMessageVisible = x

            notifyPropertyChanged(BR.noResultsMessageVisible)
        }

    fun initialize() {
        navigationService.currentActivity = KotobatenActivity.SEARCH
    }

    fun search() {
        isNoResultsMessageVisible = false

        if(!canSearch)
            return

        loadingResults = true

        val searchTask = SearchTask({
            results = it
            loadingResults = false
        })
        searchTask.execute(searchTerm)

        keyboardService.hideKeyboard()
    }

    fun clearSearchTerm() {
        searchTerm = ""
        results = listOf()
        loadingResults = false
        keyboardService.showKeyboard()
    }

    fun goToSearchResultDetail(searchResult: SearchResult) {
        navigationService.navigateToSearchResultDetail(searchResult)
    }
}