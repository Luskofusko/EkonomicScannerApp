package com.example.domain.di

import com.example.domain.repository.ReceiptRepository
import com.example.domain.usecases.DeleteReceiptUseCase
import com.example.domain.usecases.EditReceiptUseCase
import com.example.domain.usecases.GetReceiptsUseCase
import com.example.domain.usecases.InsertReceiptUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideInsertReceiptUseCase(repository: ReceiptRepository): InsertReceiptUseCase {
        return InsertReceiptUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetReceiptsUseCase(repository: ReceiptRepository): GetReceiptsUseCase {
        return GetReceiptsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteReceiptUseCase(repository: ReceiptRepository): DeleteReceiptUseCase {
        return DeleteReceiptUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideEditReceiptUseCase(repository: ReceiptRepository): EditReceiptUseCase {
        return EditReceiptUseCase(repository)
    }
}
