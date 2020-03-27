package com.example.hospital.di.modules

import androidx.lifecycle.ViewModelProvider
import com.example.hospital.model.RepositoryImpl
import com.example.hospital.model.HospitalsRepository
import com.example.hospital.model.database.HospitalsDatabase
import com.example.hospital.model.network.HospitalsClient
import com.example.hospital.view.MainActivity
import com.example.hospital.viewmodel.HospitalViewModel
import com.example.hospital.viewmodel.HospitalViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class HospitalViewModelModule(private val activity : MainActivity) {

    @Provides
    fun provideRepository(network : HospitalsClient, database: HospitalsDatabase) : HospitalsRepository{
        return RepositoryImpl(network, database)
    }
    @Provides
    fun provideHospitalViewModelFactory(repository: HospitalsRepository): HospitalViewModelFactory {
        return HospitalViewModelFactory(repository)
    }
    @Provides
    fun provideHospitalViewModel(factory: HospitalViewModelFactory) : HospitalViewModel {
        return ViewModelProvider(activity, factory).get(HospitalViewModel::class.java)
    }
}
