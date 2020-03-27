package com.example.hospital.di.components

import com.example.hospital.di.modules.DatabaseModule
import com.example.hospital.di.modules.NetworkModule
import com.example.hospital.model.database.HospitalsDatabase
import com.example.hospital.model.network.HospitalsClient
import com.example.hospital.util.MyApp
import dagger.Component

@Component(modules = [NetworkModule::class, DatabaseModule::class])
interface AppComponent {

    fun inject(application: MyApp)

    fun database() : HospitalsDatabase

    fun network() : HospitalsClient
}
