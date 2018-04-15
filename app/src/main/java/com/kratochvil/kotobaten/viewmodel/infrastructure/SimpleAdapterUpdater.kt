package com.kratochvil.kotobaten.viewmodel.infrastructure

import android.databinding.BaseObservable
import android.databinding.Observable
import android.widget.ListView

class SimpleAdapterUpdater<TEntity: Any, TViewModel: BaseObservable>(
        private val viewModel: TViewModel,
        private val viewModelPropertyId: Int,
        private val targetListView: ListView,
        private val adapterFactory: (viewModel: TViewModel) -> SimpleAdapterBase<TEntity>
) {
    init {
        viewModel.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                onViewModelPropertyChanged(propertyId)
            }
        })
    }

    private fun onViewModelPropertyChanged(propertyId: Int) {
        if(propertyId == viewModelPropertyId)
            targetListView.adapter = adapterFactory(viewModel)
    }
}