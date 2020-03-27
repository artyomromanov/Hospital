package com.example.hospital.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.hospital.model.HospitalsRepository

@Suppress("UNCHECKED_CAST")
class HospitalViewModelFactory(private val repository: HospitalsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HospitalViewModel(repository) as T
    }
}