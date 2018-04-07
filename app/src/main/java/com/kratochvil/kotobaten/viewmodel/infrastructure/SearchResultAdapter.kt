package com.kratochvil.kotobaten.viewmodel.infrastructure

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.model.entity.SearchResult

class SearchResultAdapter(context: Context, list: List<SearchResult>)
    : SimpleAdapterBase<SearchResult>(context, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.search_result, parent, false)

        val kanjiTextView = view.findViewById<TextView>(R.id.search_result_kanji_text_view)
        val kanaTextView = view.findViewById<TextView>(R.id.search_result_kana_text_view)
        val englishTextView = view.findViewById<TextView>(R.id.search_result_english_text_view)

        val searchResult = getItem(position) as SearchResult

        kanjiTextView.text = searchResult.japaneseWord
        kanaTextView.text = searchResult.japaneseReading
        englishTextView.text = searchResult.definitions
                .first()
                .getEnglishDefinitionsAsString()

        return view
    }
}