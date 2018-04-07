package com.kratochvil.kotobaten.model.entity

import java.io.Serializable

class SearchResult(
        val isCommon: Boolean,
        val japaneseWord: String,
        val japaneseReading: String,
        val definitions: List<SearchResultDefinition>)
    : Serializable

