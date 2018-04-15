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
import com.kratochvil.kotobaten.view.services.PageNavigationService
import com.kratochvil.kotobaten.view.services.VirtualKeyboardService
import com.kratochvil.kotobaten.viewmodel.SearchViewModel
import com.kratochvil.kotobaten.viewmodel.infrastructure.SearchResultAdapter
import com.kratochvil.kotobaten.viewmodel.infrastructure.SimpleAdapterUpdater
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment: Fragment() {
    private val virtualKeyboardService = VirtualKeyboardService()

    private val viewModel = SearchViewModel(
            virtualKeyboardService,
            PageNavigationService(
                    { activity },
                    { startActivity(it) }
            )
    )

    private var resultsAdapterUpdater: SimpleAdapterUpdater<SearchResult, SearchViewModel>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        initializeViewModelServices()

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

    private fun initializeViewModelServices() {
        virtualKeyboardService.initialize(
                { activity.currentFocus },
                { activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager },
                {
                    search_search_field.requestFocus()
                    val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.toggleSoftInputFromWindow(
                            search_search_field.applicationWindowToken,
                            InputMethodManager.SHOW_FORCED, 0)
                }
        )
    }
}