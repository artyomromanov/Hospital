package com.example.hospital.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Hospital::class], exportSchema = false, version = 1)
abstract class HospitalsDatabase() : RoomDatabase(){

    companion object {

        @Volatile
        private var INSTANCE : HospitalsDatabase? = null

        fun getInstance(context: Context): HospitalsDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {

                    instance = Room.databaseBuilder(

                        context.applicationContext,
                        HospitalsDatabase::class.java,"Hospital Database")
                        .fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance

                }
                return instance
            }
        }
    }
}