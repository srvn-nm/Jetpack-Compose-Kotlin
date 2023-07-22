package com.example.roomdb.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory
import com.example.roomdb.data.ConverterRepository

class ConverterViewModelFactory(private val repository: ConverterRepository) : NewInstanceFactory(){
    override fun <T : ViewModel> create(modelClass: Class<T>): T = ConverterViewModel(repository) as T
}