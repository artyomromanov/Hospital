package com.example.hospital.model

import com.example.hospital.model.database.Hospital
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody

interface HospitalsRepository {

    fun downloadHospitalData() : Single<ResponseBody>
    fun cacheAllHospitalsData(data: List<Hospital>): Completable
    fun getHospitalsFromCache(query : String = "") : Single<List<Hospital>>
}