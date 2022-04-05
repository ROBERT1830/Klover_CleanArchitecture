package com.robertconstantindinescu.domain.repository

import com.robertconstantindinescu.domain.model.MelanomaRecord
import kotlinx.coroutines.flow.Flow

interface MelanomaRepositoryInterface {

    fun getMelanomaRecords():Flow<List<MelanomaRecord>>

    suspend fun insertMelanomaRecord(melanomaRecord: MelanomaRecord)

    suspend fun deleteMelanomaRecord(melanomaRecord: MelanomaRecord)

    suspend fun deleteAllMelanomaRecords()
}