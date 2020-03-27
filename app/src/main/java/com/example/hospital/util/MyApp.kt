package com.example.hospital.util

import android.app.Application
import com.example.hospital.di.components.AppComponent
import com.example.hospital.di.components.DaggerAppComponent
import com.example.hospital.di.modules.DatabaseModule
import com.example.hospital.di.modules.NetworkModule

class MyApp : Application(){

    override fun onCreate() {
        component().inject(this)
        super.onCreate()
    }
    fun component() : AppComponent {

        return DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule(this))
            .build()
    }
}