package com.example.hospital.model

import com.example.hospital.model.database.Hospital
import io.reactivex.Single

interface HospitalsRepository {

    fun getHospitalData() : Single<List<Hospital>>

}