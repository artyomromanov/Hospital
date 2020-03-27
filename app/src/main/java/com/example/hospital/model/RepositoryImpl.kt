package com.example.hospital.model

import com.example.hospital.model.database.Hospital
import com.example.hospital.model.database.HospitalsDatabase
import com.example.hospital.model.network.HospitalsClient
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val networkService : HospitalsClient, private val database : HospitalsDatabase) : HospitalsRepository {

    override fun downloadHospitalData(): Single<ResponseBody> {
            return networkService
                .getAllHospitalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun cacheAllHospitalsData(data: List<Hospital>): Completable {
        return database
            .hospitalsDao()
            .cacheAllHospitals(data)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getHospitalsFromCache(query: String): Single<List<Hospital>> {
        return database
            .hospitalsDao()
            .getHospitals(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}