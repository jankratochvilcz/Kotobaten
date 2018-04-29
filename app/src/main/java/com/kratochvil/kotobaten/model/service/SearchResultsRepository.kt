package com.kratochvil.kotobaten.model.service

import com.kratochvil.kotobaten.model.entity.SearchResult

interface SearchResultsRepository {
    fun onSearchResultVisited(searchResult: SearchResult): SearchResult
    fun getVisitedSearchResults(): List<SearchResult>
    fun toggleIsFavorited(searchResult: SearchResult): SearchResult
}