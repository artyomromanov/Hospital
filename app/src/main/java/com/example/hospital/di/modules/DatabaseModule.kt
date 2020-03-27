package com.example.hospital.di.modules

import android.content.Context
import com.example.hospital.model.database.HospitalsDatabase
import dagger.Module
import dagger.Provides

@Module
class DatabaseModule(private val context: Context) {

    @Provides
    fun getDatabaseInstance() : HospitalsDatabase {
        return HospitalsDatabase.getInstance(context)
    }
}
