package com.example.hospital.model

import android.util.Log
import com.example.hospital.model.database.Hospital
import com.example.hospital.model.database.HospitalsDatabase
import com.example.hospital.model.network.HospitalsClient
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import javax.inject.Inject

class RepositoryImpl @Inject constructor(private val networkService : HospitalsClient, private val database : HospitalsDatabase) : HospitalsRepository {

    override fun downloadHospitalData(): Single<List<Hospital>> {
        return Single.fromCallable { parseHospitalData(networkService.getAllHospitalData().blockingGet()) }
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

    private fun parseHospitalData(data: ResponseBody): List<Hospital> {
        val inputStream: InputStream = data.byteStream()
        val streamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
        val reader = BufferedReader(streamReader)
        val hospitals = mutableListOf<Hospital>()
        try {
            while (reader.readLine() != null) {
                val line = reader.readLine().replace('\uFFFD', ';').replace(";;", ";")
                val propertiesList = line.split(';')
                hospitals.add(
                    Hospital(
                        propertiesList[0],
                        propertiesList.getOrNull(1),
                        propertiesList.getOrNull(2),
                        propertiesList.getOrNull(3),
                        propertiesList.getOrNull(4),
                        propertiesList.getOrNull(5),
                        propertiesList.getOrNull(6),
                        propertiesList[7],
                        propertiesList.getOrNull(8),
                        propertiesList.getOrNull(9),
                        propertiesList.getOrNull(10),
                        propertiesList.getOrNull(11),
                        propertiesList.getOrNull(12),
                        propertiesList.getOrNull(13),
                        propertiesList.getOrNull(14),
                        propertiesList.getOrNull(15),
                        propertiesList.getOrNull(16),
                        propertiesList.getOrNull(17),
                        propertiesList.getOrNull(18),
                        propertiesList.getOrNull(19),
                        propertiesList.getOrNull(20),
                        propertiesList.getOrNull(21)
                    )
                )
            }
        } catch (e: RuntimeException) {
            println(e.message)
        }
        return hospitals
    }
}