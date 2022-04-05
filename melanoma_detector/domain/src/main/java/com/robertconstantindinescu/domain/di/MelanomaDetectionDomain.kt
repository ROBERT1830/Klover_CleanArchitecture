package com.robertconstantindinescu.domain.di

import com.robertconstantindinescu.domain.repository.MelanomaRepositoryInterface
import com.robertconstantindinescu.domain.use_case.*
import com.robertconstantindinescu.domain.use_case.global.MelanomaDetectionUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object MelanomaDetectionDomain {

    @Provides
    @ViewModelScoped
    fun provideMelanomaDetectionUseCases(
        repositoryInterface: MelanomaRepositoryInterface
    ): MelanomaDetectionUseCases{
        return MelanomaDetectionUseCases(
            deleteMelanomaRecord = DeleteMelanomaRecordUseCase(repositoryInterface),
            getMelanomaRecords = GetMelanomaRecordsUseCase(repositoryInterface),
            insertMelanomaRecord = InsertMelanomaRecordUseCase(repositoryInterface),
            generateDate = GenerateDate(),
            removeFromSelectedCancerRecords = AddRemoveFromSelectedCancerRecords(),
            deleteAllMelanomaRecords = DeleteAllMelanomaRecords(repositoryInterface)
        )
    }
}