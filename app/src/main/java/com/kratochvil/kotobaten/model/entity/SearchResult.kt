package com.kratochvil.kotobaten.model.entity

class SearchResult(
        val isCommon: Boolean,
        val japaneseWord: String,
        val japaneseReading: String,
        val englishTranslations: List<String>) {

    var english: String = ""
        get() = englishTranslations
                .reduce({acc, curr -> "$acc; $curr"})
}