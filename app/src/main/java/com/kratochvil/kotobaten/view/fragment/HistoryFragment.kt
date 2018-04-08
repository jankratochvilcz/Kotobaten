package com.kratochvil.kotobaten.view.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kratochvil.kotobaten.R
import com.kratochvil.kotobaten.databinding.FragmentHistoryBinding
import com.kratochvil.kotobaten.model.service.realm.RealmSearchResultsRepository
import com.kratochvil.kotobaten.view.services.PageNavigationService
import com.kratochvil.kotobaten.viewmodel.HistoryViewModel

class HistoryFragment: Fragment() {
    private val viewModel = HistoryViewModel(
            RealmSearchResultsRepository(),
            PageNavigationService(
                    { activity },
                    { startActivity(it) }
            )
    )

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = DataBindingUtil.inflate<FragmentHistoryBinding>(
                inflater ?: throw IllegalArgumentException(),
                R.layout.fragment_history,
                container,
                false)

        binding.viewModel = viewModel

        return binding.root
    }
}