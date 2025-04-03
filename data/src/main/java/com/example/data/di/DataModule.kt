package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.database.ReceiptDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ReceiptDatabase {
        return Room.databaseBuilder(
            context,
            ReceiptDatabase::class.java,
            "receipt_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideReceiptDao(database: ReceiptDatabase) = database.receiptDao()
}
