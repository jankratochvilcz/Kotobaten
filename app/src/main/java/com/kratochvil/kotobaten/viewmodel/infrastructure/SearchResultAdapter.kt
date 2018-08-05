package com.kratochvil.kotobaten.viewmodel.infrastructure

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.model.entity.SearchResult
import java.util.*

class SearchResultAdapter(
        context: Context,
        list: List<SearchResult>,
        private val groupByDate: Boolean)
    : SimpleAdapterBase<SearchResult>(context, list) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: inflater.inflate(R.layout.search_result, parent, false)

        val kanjiTextView = view.findViewById<TextView>(R.id.search_result_kanji_text_view)
        val kanaTextView = view.findViewById<TextView>(R.id.search_result_kana_text_view)
        val englishTextView = view.findViewById<TextView>(R.id.search_result_english_text_view)

        val searchResult = getItemTyped(position)

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
            resetGroupHeaderMargin(view)

            return view
        }

        val currentDateWithoutTime = getDateWithoutTime(searchResult.lastVisited)
        val previousDateWithoutTime = getDateWithoutTime(getItemTyped(position - 1).lastVisited)
        if(currentDateWithoutTime < previousDateWithoutTime)
            renderGroupHeader(view, parent, position)

        return view
    }

    private fun resetGroupHeaderMargin(view: View) {
        val groupHeaderTextView = view.findViewById<TextView>(R.id.search_result_group_title)
        val params = ConstraintLayout.LayoutParams(
                ConstraintLayout.LayoutParams.WRAP_CONTENT,
                ConstraintLayout.LayoutParams.WRAP_CONTENT)

        params.setMargins(0, 0, 0, 0)
        groupHeaderTextView.layoutParams = params
    }

    private fun renderGroupHeader(view: View, parent: ViewGroup, position: Int) {
        val groupHeaderTextView = view.findViewById<TextView>(R.id.search_result_group_title)

        groupHeaderTextView.visibility = View.VISIBLE
        groupHeaderTextView.text = getDateString(getItemTyped(position).lastVisited, parent)
    }

    private fun getDateWithoutTime(date: Date): Date {
        val calendar = Calendar.getInstance()
        calendar.time = date
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        return calendar.time
    }

    private fun getDateString(date: Date, parent: ViewGroup): String {
        val today = Calendar.getInstance()

        val dateAsCalendar = Calendar.getInstance()
        dateAsCalendar.time = date

        val daysDifference = (today.timeInMillis /(24*60*60*1000)) - (dateAsCalendar.timeInMillis /(24*60*60*1000))

        return when (daysDifference.toInt()) {
            0 -> "Today"
            1 -> "Yesterday"
            in 2..7 -> dateAsCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
            else -> DateFormat.getDateFormat(parent.context).format(date)
        }

    }
}