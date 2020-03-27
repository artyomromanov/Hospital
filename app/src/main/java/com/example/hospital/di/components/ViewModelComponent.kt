package com.example.hospital.di.components

import com.example.hospital.di.modules.HospitalViewModelModule
import com.example.hospital.view.MainActivity
import dagger.Component

@Component(modules = [HospitalViewModelModule::class], dependencies = [AppComponent::class])
interface ViewModelComponent {

    fun injectActivity(activity: MainActivity)

}