package com.kratochvil.kotobaten

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class SearchResultAdapter : BaseAdapter {
    var context: Context? = null
    var items: List<SearchResult>? = null
    var inflater: LayoutInflater? = null

    constructor(context: Context, items: List<SearchResult>) : super() {
        this.context = context
        this.items = items
        this.inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = (inflater as LayoutInflater).inflate(R.layout.search_result, parent, false)

        val kanjiTextView = view.findViewById<TextView>(R.id.search_result_kanji_text_view)
        val kanaTextView = view.findViewById<TextView>(R.id.search_result_kana_text_view)
        val englishTextView = view.findViewById<TextView>(R.id.search_result_english_text_view)

        val searchResult = getItem(position) as SearchResult

        kanjiTextView.text = searchResult.japaneseWord
        kanaTextView.text = searchResult.japaneseReading
        englishTextView.text = searchResult.englishTranslations.first()

        return view
    }

    override fun getItem(position: Int): Any {
        return if(items == null)
            Unit
        else
            (items as List<SearchResult>)[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items?.count() ?: 0
    }
}