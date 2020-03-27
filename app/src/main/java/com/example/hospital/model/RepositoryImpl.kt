package com.example.hospital.model

import com.example.hospital.model.database.HospitalsDatabase
import com.example.hospital.model.network.HospitalsClient
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val networkService : HospitalsClient, private val database : HospitalsDatabase) : HospitalsRepository {

    override fun getHospitalData(): Single<File> {
            return networkService
                .getAllHospitalData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}