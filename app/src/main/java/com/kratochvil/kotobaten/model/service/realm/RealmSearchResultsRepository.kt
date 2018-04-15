package com.kratochvil.kotobaten.model.service.realm

import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.entity.SearchResultDefinition
import com.kratochvil.kotobaten.model.service.SearchResultsRepository
import io.realm.Realm
import io.realm.Sort
import java.util.*

class RealmSearchResultsRepository: SearchResultsRepository {
    override fun onSearchResultVisited(searchResult: SearchResult)
            : SearchResult {
        val realm = Realm.getDefaultInstance()
        val existingSearchResult = realm
                .where(SearchResult::class.java)
                .equalTo(SearchResult::japaneseWord.name, searchResult.japaneseWord)
                .findFirst()

        if(existingSearchResult == null) {
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


                searchResult.visitsCount = 1

                val date = Calendar.getInstance()
                date.time = searchResult.lastVisited
                date.add(Calendar.DAY_OF_MONTH, -1)

                searchResult.lastVisited = date.time
                transaction.copyToRealm(searchResult)
            }

            return searchResult
        }

        realm.executeTransaction {
            existingSearchResult.visitsCount++
            existingSearchResult.lastVisited = searchResult.lastVisited

            if(existingSearchResult.visitsCount >= SearchResult.AUTOFAVORITE_THRESHOLD)
                existingSearchResult.isFavorited = true
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