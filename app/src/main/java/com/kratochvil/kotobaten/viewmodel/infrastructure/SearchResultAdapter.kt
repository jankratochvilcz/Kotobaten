package com.kratochvil.kotobaten.viewmodel.infrastructure

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.model.entity.SearchResult

class SearchResultAdapter(
        context: Context,
        private val items: List<SearchResult>)
    : BaseAdapter() {

    var inflater: LayoutInflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.search_result, parent, false)

        val kanjiTextView = view.findViewById<TextView>(R.id.search_result_kanji_text_view)
        val kanaTextView = view.findViewById<TextView>(R.id.search_result_kana_text_view)
        val englishTextView = view.findViewById<TextView>(R.id.search_result_english_text_view)

        val searchResult = getItem(position) as SearchResult

        kanjiTextView.text = searchResult.japaneseWord
        kanaTextView.text = searchResult.japaneseReading
        englishTextView.text = searchResult.english

        return view
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.count()
    }
}