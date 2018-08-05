package com.kratochvil.kotobaten.model.service

import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.entity.SearchResultDefinition
import io.realm.RealmList
import org.json.JSONObject
import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class JishoApiService(
        private val searchResultSerializationService: SearchResultSerializationService
) {
    fun search(term: String): List<SearchResult> {
        val urlString = "https://jisho.org/api/v1/search/words?keyword=$term"
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
                    val result = searchResultSerializationService.deserializeSearchResult(responseObject)

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
}