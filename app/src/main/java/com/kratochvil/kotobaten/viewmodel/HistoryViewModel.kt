package com.kratochvil.kotobaten.viewmodel

import android.databinding.BaseObservable
import com.kratochvil.kotobaten.model.service.NavigationService
import com.kratochvil.kotobaten.model.service.SearchResultsRepository

class HistoryViewModel(
        private val searchResultsRepository: SearchResultsRepository,
        private val navigationService: NavigationService
): BaseObservable() {
}