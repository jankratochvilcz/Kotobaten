package com.kratochvil.kotobaten.model.entity

import java.io.Serializable

class SearchResultDefinition(
        private val englishDefinitions: List<String>,
        private val partsOfSpeech: List<String>,
        private val tags: List<String>,
        private val info: List<String>): Serializable {

    fun getEnglishDefinitionsAsString(): String {
        return reduceToSemicolonDelimitedString(englishDefinitions)
    }

    fun getAdditionalInformationAsString(): String {
        return reduceToSemicolonDelimitedString(tags
                .union(info))
    }

    fun getPartsOfSpeechAsString(): String {
        return reduceToSemicolonDelimitedString(partsOfSpeech)
    }

    private fun reduceToSemicolonDelimitedString(values: Collection<String>): String {
        return if(values.any())
            values.reduce({ acc, curr -> "$acc; $curr" })
        else ""
    }
}