package com.kratochvil.kotobaten.view.fragment

import android.app.Fragment
import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.FragmentSearchBinding
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.injection.InjectionParams
import com.kratochvil.kotobaten.viewmodel.SearchViewModel
import com.kratochvil.kotobaten.viewmodel.infrastructure.SearchResultAdapter
import com.kratochvil.kotobaten.viewmodel.infrastructure.SimpleAdapterUpdater
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject

class SearchFragment: Fragment() {
    private val viewModel by inject<SearchViewModel> { mapOf(
            InjectionParams.GET_CURRENT_ACTIVITY_FUN to { activity },
            InjectionParams.SHOW_KEYBOARD_FUN to {
                this.search_search_field.requestFocus()
                val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.toggleSoftInputFromWindow(
                        this.search_search_field.applicationWindowToken,
                        InputMethodManager.SHOW_FORCED, 0)
            },
            InjectionParams.FOCUSED_VIEW_FUN to { activity.currentFocus }
    ) }

    private var resultsAdapterUpdater: SimpleAdapterUpdater<SearchResult, SearchViewModel>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val binding = DataBindingUtil.inflate<FragmentSearchBinding>(
                inflater ?: throw IllegalArgumentException(),
                R.layout.fragment_search,
                container,
                false)


        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        resultsAdapterUpdater = SimpleAdapterUpdater(
                viewModel,
                BR.results,
                search_results_list,
                { SearchResultAdapter(context, it.results, false) })

        registerUiListeners()
    }

    private fun registerUiListeners() {
        search_search_field.setOnEditorActionListener({ _, _, _ ->
            viewModel.search()
            true
        })

        search_results_list.setOnItemClickListener { _, _, position, _ ->
            viewModel.goToSearchResultDetail(viewModel.results[position])
        }
    }
}