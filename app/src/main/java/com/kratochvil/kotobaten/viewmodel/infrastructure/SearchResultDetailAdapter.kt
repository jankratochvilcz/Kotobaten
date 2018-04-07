package com.kratochvil.kotobaten.viewmodel.infrastructure

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.model.entity.SearchResultDefinition

class SearchResultDetailAdapter(context: Context, items: List<SearchResultDefinition>)
    : SimpleAdapterBase<SearchResultDefinition>(context, items) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = inflater.inflate(R.layout.search_result_definition, parent, false)

        val englishDefinitionsTextView = view.findViewById<TextView>(R.id.search_result_definition_englishDefinitions)
        val resultOrderTextView = view.findViewById<TextView>(R.id.search_result_definition_resultNumber)
        val additionalInformationTextView = view.findViewById<TextView>(R.id.seach_result_definition_additional_info)
        var partOfSpeechTextView = view.findViewById<TextView>(R.id.search_result_definition_partOfSpeech)

        val searchResult = getItem(position) as SearchResultDefinition

        englishDefinitionsTextView.text = searchResult.getEnglishDefinitionsAsString()
        resultOrderTextView.text = (position + 1).toString() + "."
        additionalInformationTextView.text = searchResult.getAdditionalInformationAsString()
        partOfSpeechTextView.text = searchResult.getPartsOfSpeechAsString()

        return view
    }
}