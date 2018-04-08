package com.kratochvil.kotobaten

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration

class KotobatenApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initializeRealm()
    }

    private fun initializeRealm() {
        Realm.init(this)


        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}