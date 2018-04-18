package com.kratochvil.kotobaten.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.FragmentHistoryBinding
import com.kratochvil.kotobaten.model.entity.SearchResult
import com.kratochvil.kotobaten.model.service.injection.InjectionParams
import com.kratochvil.kotobaten.viewmodel.HistoryViewModel
import com.kratochvil.kotobaten.viewmodel.infrastructure.SearchResultAdapter
import com.kratochvil.kotobaten.viewmodel.infrastructure.SimpleAdapterUpdater
import kotlinx.android.synthetic.main.fragment_history.*
import org.koin.android.ext.android.inject

class HistoryFragment: Fragment() {
    private val viewModel by inject<HistoryViewModel> { mapOf(
            InjectionParams.GET_CURRENT_ACTIVITY_FUN to { activity }
    ) }

    private var resultsAdapterUpdater: SimpleAdapterUpdater<SearchResult, HistoryViewModel>? = null

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentHistoryBinding>(
                inflater ?: throw IllegalArgumentException(),
                R.layout.fragment_history,
                container,
                false)

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        resultsAdapterUpdater = SimpleAdapterUpdater(
                viewModel,
                BR.results,
                history_results_list_view,
                { SearchResultAdapter(context, it.results, true) })

        history_results_list_view.setOnItemClickListener { _, _, position, _ ->
            viewModel.goToSearchResultDetail(viewModel.results[position])
        }

        viewModel.initialize()
    }
}