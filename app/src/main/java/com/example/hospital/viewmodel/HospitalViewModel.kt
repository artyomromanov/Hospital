package com.example.hospital.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hospital.model.HospitalsRepository
import com.example.hospital.model.database.Hospital
import io.reactivex.disposables.CompositeDisposable
import okhttp3.ResponseBody
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset

class HospitalViewModel(private val repository: HospitalsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val hospitalData = MutableLiveData<List<Hospital>>()
    private val errorData = MutableLiveData<String>()
    private val databaseCacheData = MutableLiveData<Boolean>()
    private val networkDownloadData = MutableLiveData<Boolean>()

    fun downloadAllHospitalsOnline() {
        compositeDisposable.add(
            repository
                .downloadHospitalData()
                .subscribe(
                    { data -> cacheAllHospitalsData(parseData(data)); networkDownloadData.value = true; },
                    { error -> errorData.value = error.message })
        )
    }

    private fun cacheAllHospitalsData(data: List<Hospital>) {
        compositeDisposable.add(
            repository
                .cacheAllHospitalsData(data)
                .subscribe({ databaseCacheData.value = true }, { errorData.value = it.message })
        )
    }

    fun getHospitalData(query: String = "") {
        compositeDisposable.add(
            repository
                .getHospitalsFromCache(query)
                .subscribe(
                { data -> hospitalData.value = data},
                { error -> errorData.value = error.message})
        )
    }

    fun getHospitalLiveData() = hospitalData as LiveData<List<Hospital>>
    fun getErrorData() = errorData as LiveData<String>
    fun getDatabaseCacheData() = databaseCacheData as LiveData<Boolean>
    fun getNetworkDownloadData() = networkDownloadData as LiveData<Boolean>

    private fun parseData(data: ResponseBody): List<Hospital> {
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