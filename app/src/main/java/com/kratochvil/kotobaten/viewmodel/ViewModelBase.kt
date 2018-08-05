package com.kratochvil.kotobaten.viewmodel

import android.databinding.BaseObservable
import com.kratochvil.kotobaten.model.service.navigation.KotobatenActivity
import com.kratochvil.kotobaten.model.service.navigation.NavigationService

open class ViewModelBase(
        private val navigationService: NavigationService
) : BaseObservable() {

    fun onNavigatedTo(activityType: KotobatenActivity) {
        navigationService.currentActivity = activityType
    }
}