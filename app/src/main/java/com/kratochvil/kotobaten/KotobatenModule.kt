package com.kratochvil.kotobaten

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.inputmethod.InputMethodManager
import com.kratochvil.kotobaten.model.service.*
import com.kratochvil.kotobaten.model.service.injection.InjectionParams
import com.kratochvil.kotobaten.model.service.realm.RealmSearchResultsRepository
import com.kratochvil.kotobaten.view.services.PageNavigationService
import com.kratochvil.kotobaten.view.services.VirtualKeyboardService
import com.kratochvil.kotobaten.viewmodel.HistoryViewModel
import com.kratochvil.kotobaten.viewmodel.SearchResultDetailViewModel
import com.kratochvil.kotobaten.viewmodel.SearchViewModel
import org.koin.dsl.module.Module

class KotobatenModule {
    fun getModule(applicationContext: Context, startActivity: (intent: Intent) -> Unit): Module {
        return org.koin.dsl.module.applicationContext {
            factory { RealmSearchResultsRepository() as SearchResultsRepository }

            factory { SearchResultSerializationService() }

            factory { JishoApiService(get()) }

            factory { params ->
                PageNavigationService(
                        { applicationContext },
                        { startActivity(it) },
                        params[InjectionParams.GET_CURRENT_ACTIVITY_FUN])
            } bind NavigationService::class

            bean { params ->
                VirtualKeyboardService(
                        params[InjectionParams.FOCUSED_VIEW_FUN],
                        {
                            val param: () -> Activity = params[InjectionParams.GET_CURRENT_ACTIVITY_FUN]
                            param().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        },
                        params[InjectionParams.SHOW_KEYBOARD_FUN]) as KeyboardService
            }

            factory { params -> HistoryViewModel(get { params.values }, get { params.values }) }
            factory { params -> SearchResultDetailViewModel(get { params.values}, get { params.values }) }
            factory { params -> SearchViewModel(get { params.values }, get { params.values}) }
        }
    }
}