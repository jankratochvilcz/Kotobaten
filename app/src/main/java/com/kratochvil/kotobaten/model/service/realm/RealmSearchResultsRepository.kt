package com.kratochvil.kotobaten.model.service.realm

import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.entity.SearchResultDefinition
import com.kratochvil.kotobaten.model.service.SearchResultsRepository
import io.realm.Realm
import io.realm.Sort
import java.util.*

class RealmSearchResultsRepository: SearchResultsRepository {
    override fun toggleIsFavorited(searchResult: SearchResult): SearchResult {
        val existingSearchResult = updateOrAddSearchResult(searchResult, false)

        Realm.getDefaultInstance().executeTransaction {
            existingSearchResult.isFavorited = !existingSearchResult.isFavorited
        }

        return existingSearchResult
    }

    override fun onSearchResultVisited(searchResult: SearchResult)
            : SearchResult {
        return updateOrAddSearchResult(searchResult, true)
    }

    private fun updateOrAddSearchResult(
            searchResult: SearchResult,
            resultVisited: Boolean): SearchResult {
        val realm = Realm.getDefaultInstance()
        val existingSearchResult = realm
                .where(SearchResult::class.java)
                .equalTo(SearchResult::japaneseWord.name, searchResult.japaneseWord)
                .findFirst()

        if (existingSearchResult == null) {
            realm.executeTransaction { transaction ->
                val searchResultPreviousMaxId = transaction
                        .where(SearchResult::class.java)
                        .max(SearchResult::id.name)?.toLong() ?: 0L

                var searchResultDefinitionPreviousMaxId = transaction
                        .where(SearchResultDefinition::class.java)
                        .max(SearchResult::id.name)?.toLong() ?: 0L

                searchResult.id = searchResultPreviousMaxId + 1

                for (definition in searchResult.definitions) {
                    searchResultDefinitionPreviousMaxId++
                    definition.id = searchResultDefinitionPreviousMaxId
                }

                searchResult.visitsCount = if(resultVisited) 1 else 0
                searchResult.lastVisited = Date()
                transaction.copyToRealm(searchResult)
            }

            return searchResult
        }

        if(resultVisited) {
            realm.executeTransaction {
                existingSearchResult.visitsCount++
                existingSearchResult.lastVisited = searchResult.lastVisited
            }
        }

        return existingSearchResult
    }

    override fun getVisitedSearchResults(): List<SearchResult> {
        return Realm.getDefaultInstance()
                .where(SearchResult::class.java)
                .sort(SearchResult::lastVisited.name, Sort.DESCENDING)
                .findAll()
    }
}