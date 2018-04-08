package com.kratochvil.kotobaten.model.service

import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.entity.SearchResultDefinition
import io.realm.RealmList
import org.json.JSONObject

class SearchResultSerializationService {
    private val japaneseObjectName = "japanese"
    private val sensesObjectName = "senses"
    private val wikipediaDefinitionResultType = "Wikipedia definition"
    private val japaneseReadingPropertyName = "reading"
    private val japaneseWordPropertyName = "word"
    private val isCommonPropertyName = "is_common"

    fun deserializeSearchResult(jsonObject: JSONObject): SearchResult {
        val japaneseObject = jsonObject
                .getJSONArray(japaneseObjectName)
                .getJSONObject(0)

        val englishDefinitionsArray = jsonObject
                .getJSONArray(sensesObjectName)

        val searchResultDefinitions = RealmList<SearchResultDefinition>()
        for(i in 0..(englishDefinitionsArray.length() - 1)) {
            val searchResultDefinition = getSearchResultDefinition(
                    englishDefinitionsArray.getJSONObject(i))

            // These results are irrelevant, so I parse them out here
            if(searchResultDefinition.getPartsOfSpeechAsString() != wikipediaDefinitionResultType)
                searchResultDefinitions.add(searchResultDefinition)
        }

        val result = SearchResult()

        result.isCommon = jsonObject.getBoolean(isCommonPropertyName)
        result.japaneseWord = japaneseObject.getString(japaneseWordPropertyName)
        result.japaneseReading = japaneseObject.getString(japaneseReadingPropertyName)
        result.definitions = searchResultDefinitions

        return result
    }

    private fun getSearchResultDefinition(jsonSearchResultDefinition: JSONObject): SearchResultDefinition {
        val result = SearchResultDefinition()

        result.englishDefinitions = getRealmListFromProperty(
                jsonSearchResultDefinition,
                "english_definitions")

        result.partsOfSpeech = getRealmListFromProperty(
                jsonSearchResultDefinition,
                "parts_of_speech")

        result.info = getRealmListFromProperty(
                jsonSearchResultDefinition,
                "info")

        result.tags = getRealmListFromProperty(
                jsonSearchResultDefinition,
                "tags")

        return result
    }

    private fun getRealmListFromProperty(
            jsonObject: JSONObject,
            propertyName: String): RealmList<String> {

        val array = jsonObject.getJSONArray(propertyName)

        val result = RealmList<String>()

        for(i in 0..(array.length() - 1))
            result.add(array.getString(i))

        return result
    }
}