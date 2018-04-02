package com.kratochvil.kotobaten.model.entity

import java.io.Serializable

class SearchResult(
        val isCommon: Boolean,
        val japaneseWord: String,
        val japaneseReading: String,
        val englishTranslations: List<String>)
    : Serializable{

    var english: String = ""
        get() = englishTranslations
                .reduce({acc, curr -> "$acc; $curr"})
}