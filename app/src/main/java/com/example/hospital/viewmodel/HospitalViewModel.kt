package com.example.hospital.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hospital.model.HospitalsRepository
import com.example.hospital.model.database.Hospital
import io.reactivex.disposables.CompositeDisposable

class HospitalViewModel(private val repository: HospitalsRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val hospitalsData = MutableLiveData<String>()
    private val errorData = MutableLiveData<String>()

    fun getAllHospitals() {
        compositeDisposable.add(
            repository
                .getHospitalData()
                .subscribe({ data -> data.name },{ error -> errorData.value = error.message })
        )
    }

    fun getHospitalsData() = hospitalsData as LiveData<String>
    fun getErrorData() = errorData as LiveData<String>

}