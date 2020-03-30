package com.example.hospital.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hospital.model.HospitalsRepository
import com.example.hospital.model.database.Hospital
import com.example.hospital.util.NO_HOSPITAL_FOUND

import com.example.hospital.util.UNKNOWN_ERROR
import io.reactivex.disposables.CompositeDisposable

class HospitalViewModel(private val repository: HospitalsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private val hospitalData = MutableLiveData<HospitalLoadingState>()

    private val databaseUpdateData = MutableLiveData<Boolean>()


    fun downloadAllHospitalsOnline() {
        compositeDisposable.add(
            repository
                .downloadHospitalData()
                .subscribe({
                    if (it.isEmpty()) {
                        hospitalData.value = HospitalLoadingState.ERROR(NO_HOSPITAL_FOUND)
                    } else {
                        hospitalData.value = HospitalLoadingState.SUCCESS(it)
                        cacheAllHospitalsData(it)
                    }
                }, { error ->
                    hospitalData.value = HospitalLoadingState.ERROR(error.message ?: UNKNOWN_ERROR)
                })
        )
    }

    private fun cacheAllHospitalsData(data: List<Hospital>) {
        compositeDisposable.add(
            repository
                .cacheAllHospitalsData(data)
                .subscribe({ databaseUpdateData.value = true }, {databaseUpdateData.value = false})
        )
    }

    /*fun getHospitalData(query: String = "") {
        compositeDisposable.add(
            repository
                .getHospitalsFromCache(query)
                .subscribe(
                    { data -> hospitalData.value = data },
                    { error -> errorData.value = error.message })
        )
    }*/

    sealed class HospitalLoadingState {
        object IN_PROGRESS : HospitalLoadingState()
        data class SUCCESS(val hospitalsData : List<Hospital>) : HospitalLoadingState()
        data class ERROR(val message : String) : HospitalLoadingState()
    }

    fun getHospitalLiveData() = hospitalData as LiveData<HospitalLoadingState>
    fun getDatabaseUpdateData() = databaseUpdateData as LiveData<Boolean>

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}