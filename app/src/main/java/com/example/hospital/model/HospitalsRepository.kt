package com.example.hospital.model

import com.example.hospital.model.database.Hospital
import io.reactivex.Single
import java.io.File

interface HospitalsRepository {

    fun getHospitalData() : Single<File>

}