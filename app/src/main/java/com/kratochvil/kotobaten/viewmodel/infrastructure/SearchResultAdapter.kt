package com.kratochvil.kotobaten.viewmodel.infrastructure

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.model.entity.SearchResult
import android.text.format.DateFormat
import java.util.*

class SearchResultAdapter(
        context: Context,
        list: List<SearchResult>,
        private val groupByDate: Boolean)
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
                ?.getEnglishDefinitionsAsString()

        if(!groupByDate)
            return view

        if(parent == null)
            throw IllegalArgumentException()

        if(position == 0) {
            renderGroupHeader(view, parent, position)
            return view
        }

        val currentDateWithoutTime = getDateWithoutTime(getItemTyped(position).lastVisited)
        val previousDateWithoutTime = getDateWithoutTime(getItemTyped(position - 1).lastVisited)
        if(currentDateWithoutTime < previousDateWithoutTime)
            renderGroupHeader(view, parent, position)

        return view
    }

    private fun renderGroupHeader(view: View, parent: ViewGroup, position: Int) {
        val groupHeaderTextView = view.findViewById<TextView>(R.id.search_result_group_title)

        groupHeaderTextView.visibility = View.VISIBLE

        val lastVisited = getItemTyped(position).lastVisited
        groupHeaderTextView.text = DateFormat.getDateFormat(parent.context).format(lastVisited)
    }

    private fun getDateWithoutTime(date:Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.time
    }
}