package com.kratochvil.kotobaten

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.startKoin

class KotobatenApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initializeRealm()

        startKoin(this, listOf(
                KotobatenModule().getModule(applicationContext, {startActivity(it)})))
    }

    private fun initializeRealm() {
        Realm.init(this)


        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}