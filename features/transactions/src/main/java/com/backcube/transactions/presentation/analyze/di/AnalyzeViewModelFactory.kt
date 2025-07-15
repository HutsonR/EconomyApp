package com.backcube.transactions.presentation.analyze.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.backcube.transactions.presentation.analyze.AnalyzeViewModel

class AnalyzeViewModelFactory(
    private val assistedFactory: AnalyzeViewModel.Factory,
    private val isIncome: Boolean
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return assistedFactory.create(isIncome) as T
    }
}
