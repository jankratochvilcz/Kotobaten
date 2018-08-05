package com.kratochvil.kotobaten

import android.app.Application
import io.realm.Realm
import io.realm.RealmConfiguration
import org.koin.android.ext.android.startKoin
import com.microsoft.appcenter.crashes.Crashes
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.AppCenter



class KotobatenApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        initializeRealm()

        startKoin(this, listOf(
                KotobatenModule().getModule(applicationContext, {startActivity(it)})))

        AppCenter.start(this, "9e2d1de6-4251-476b-8a8a-6fb3d0fd7029",
                Analytics::class.java, Crashes::class.java)
    }

    private fun initializeRealm() {
        Realm.init(this)


        val config = RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build()

        Realm.setDefaultConfiguration(config)
    }
}