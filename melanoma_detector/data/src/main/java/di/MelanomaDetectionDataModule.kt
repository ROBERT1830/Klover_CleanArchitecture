package com.robertconstantindinescu.data.di

import android.app.Application
import androidx.room.Room
import com.robertconstantindinescu.data.local.MelanomaDatabase
import com.robertconstantindinescu.data.repository.MelanomaRepositoryImpl
import com.robertconstantindinescu.domain.repository.MelanomaRepositoryInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MelanomaDetectionDataModule {

    @Provides
    @Singleton
    fun provideMelanomaDatabase(app: Application): MelanomaDatabase{
        return Room.databaseBuilder(
            app,
            MelanomaDatabase::class.java,
            MelanomaDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMelanomaRepositoryInterface(
        db:MelanomaDatabase
    ):MelanomaRepositoryInterface{
        return MelanomaRepositoryImpl(
            dao = db.dao
        )
    }
}