package com.example.hospital

import io.reactivex.Single

interface Repository {

    fun getHospitalData() : Single<List<Hospital>>

}