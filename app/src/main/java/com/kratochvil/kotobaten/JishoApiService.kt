package com.kratochvil.kotobaten

import org.json.JSONArray
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

                    val japaneseObject = responseObject.getJSONArray("japanese").getJSONObject(0)

                    val englishDefinitionsArray = responseObject
                            .getJSONArray("senses").getJSONObject(0)
                            .getJSONArray("english_definitions")

                    val englishDefinitions = ArrayList<String>()
                    for(i in 0..(englishDefinitionsArray.length() - 1))
                        englishDefinitions.add(englishDefinitionsArray.getString(i))

                    val result = SearchResult(
                            responseObject.getBoolean("is_common"),
                            japaneseObject.getString("word"),
                            japaneseObject.getString("reading"),
                            englishDefinitions)

                    results.add(result)
                }
                catch (exception: Exception) {}
            }

            return results
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