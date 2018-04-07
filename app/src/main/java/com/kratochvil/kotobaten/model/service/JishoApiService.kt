package com.kratochvil.kotobaten.model.service

import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.entity.SearchResultDefinition
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class JishoApiService {
    fun search(term: String): List<SearchResult> {
        val urlString = "http://jisho.org/api/v1/search/words?keyword=$term"
        val targetUrl = URL(urlString)
        val connection = targetUrl.openConnection() as HttpURLConnection

        try {
            val stream = BufferedInputStream(connection.inputStream)
            val reader = BufferedReader(InputStreamReader(stream))

            val builder = StringBuilder()

            while(true) {
                val line = reader.readLine() ?: break
                builder.append(line)
            }

            val responseString = builder.toString()

            val responsesArray = JSONObject(responseString).getJSONArray("data")

            val results = ArrayList<SearchResult>()

            for (i in 0..(responsesArray.length() - 1)) {
                try {
                    val responseObject = responsesArray.getJSONObject(i)

                    val japaneseObject = responseObject
                            .getJSONArray("japanese")
                            .getJSONObject(0)

                    val englishDefinitionsArray = responseObject
                            .getJSONArray("senses")

                    val searchResultDefinitions = ArrayList<SearchResultDefinition>()
                    for(i in 0..(englishDefinitionsArray.length() - 1)) {
                        val searchResultDefinition = getSearchResultDefinition(
                                englishDefinitionsArray.getJSONObject(i))

                        if(searchResultDefinition.getPartsOfSpeechAsString() != "Wikipedia definition")
                            searchResultDefinitions.add(searchResultDefinition)
                    }

                    val result = SearchResult(
                            responseObject.getBoolean("is_common"),
                            japaneseObject.getString("word"),
                            japaneseObject.getString("reading"),
                            searchResultDefinitions)

                    results.add(result)
                }
                catch (exception: Exception) {}
            }

            return results
                    .filter { it.definitions.any() }
        }
        catch (ex: Exception) {
            print(ex)
        }
        finally {
            connection.disconnect()
        }

        return listOf()
    }

    private fun getSearchResultDefinition(jsonSearchResultDefinition: JSONObject): SearchResultDefinition {
        val englishDefinitions = getArrayOfStringsFromProperty(
                jsonSearchResultDefinition,
                "english_definitions")

        val partsOfSpeech = getArrayOfStringsFromProperty(
                jsonSearchResultDefinition,
                "parts_of_speech")

        val info = getArrayOfStringsFromProperty(
                jsonSearchResultDefinition,
                "info")

        val tags = getArrayOfStringsFromProperty(
                jsonSearchResultDefinition,
                "tags")

        return SearchResultDefinition(
                englishDefinitions,
                partsOfSpeech,
                tags,
                info)
    }

    private fun getArrayOfStringsFromProperty(
            jsonObject: JSONObject,
            propertyName: String): List<String> {

        val array = jsonObject.getJSONArray(propertyName)

        val result = ArrayList<String>()

        for(i in 0..(array.length() - 1))
            result.add(array.getString(i))

        return result
    }
}