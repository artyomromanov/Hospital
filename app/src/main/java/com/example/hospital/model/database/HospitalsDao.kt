package com.example.hospital.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface HospitalsDao {

        @Query("SELECT * FROM hospitals WHERE organisationName LIKE '%' || :query  || '%'")
        fun getHospitals(query: String): Single<List<Hospital>>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun cacheAllHospitals(result: List<Hospital>): Completable

}