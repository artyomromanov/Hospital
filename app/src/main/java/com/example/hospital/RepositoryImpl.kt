package com.example.hospital

import io.reactivex.Single

class RepositoryImpl(private val networkService : NetworkService, private val database : DatabaseService) : Repository{

    override fun getHospitalData(): Single<List<Hospital>> {

        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}