package com.kratochvil.kotobaten.viewmodel

import android.databinding.BaseObservable
import android.databinding.Bindable
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.NavigationService

class SearchResultDetailViewModel(
        private val navigationService: NavigationService
)
    :BaseObservable() {

    private var _searchResult = SearchResult(false, "", "", listOf())

    var searchResult: SearchResult
        @Bindable get() = _searchResult
        set (x) {
            _searchResult = x
            notifyPropertyChanged(BR.searchResult)
        }

}