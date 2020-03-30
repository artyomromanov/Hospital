package com.example.hospital.model.network

import com.example.hospital.util.HOSPITALS_ENDPOINT
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.GET

interface HospitalsClient {

    @GET(HOSPITALS_ENDPOINT)
    fun getAllHospitalData() : Single<ResponseBody>

}
