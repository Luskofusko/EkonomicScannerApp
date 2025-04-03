package com.example.presentation.di

import com.example.domain.usecases.GetReceiptsUseCase
import com.example.domain.usecases.InsertReceiptUseCase
import com.example.presentation.viewmodel.ReceiptViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object PresentationModule {

    @Provides
    @ViewModelScoped
    fun provideReceiptViewModel(
        insertReceiptUseCase: InsertReceiptUseCase,
        getReceiptsUseCase: GetReceiptsUseCase
    ): ReceiptViewModel {
        return ReceiptViewModel(insertReceiptUseCase, getReceiptsUseCase)
    }
}
