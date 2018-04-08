package com.kratochvil.kotobaten.model.service.realm

import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.entity.SearchResultDefinition
import com.kratochvil.kotobaten.model.service.SearchResultsRepository
import io.realm.Realm

class RealmSearchResultsRepository: SearchResultsRepository {
    override fun onSearchResultVisited(searchResult: SearchResult)
            : SearchResult {
        val realm = Realm.getDefaultInstance()
        val existingSearchResult = Realm.getDefaultInstance()
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
                transaction.copyToRealm(searchResult)
            }

            return searchResult
        }

        realm.executeTransaction {
            existingSearchResult.visitsCount++
        }

        return existingSearchResult
    }
}