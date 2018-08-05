package com.kratochvil.kotobaten.viewmodel

import android.databinding.Bindable
import com.kratochvil.kotobaten.BR
import com.kratochvil.kotobaten.model.service.navigation.KotobatenActivity
import com.kratochvil.kotobaten.model.service.navigation.NavigationService

class MainViewModel(
        private val navigationService: NavigationService
): ViewModelBase(navigationService) {

    private var _currentActivity: KotobatenActivity = KotobatenActivity.UNKNOWN

    var currentActivity: KotobatenActivity
        @Bindable get() = _currentActivity
        set(x) {
            _currentActivity = x
            notifyPropertyChanged(BR.currentActivity)
        }

    fun initialize() {
        navigationService.addNavigatedListener(NavigatedListener())
    }

    inner class NavigatedListener
        : com.kratochvil.kotobaten.model.service.navigation.NavigatedListener {
        override fun onNavigated(activity: KotobatenActivity) {
            currentActivity = activity
        }

    }
}