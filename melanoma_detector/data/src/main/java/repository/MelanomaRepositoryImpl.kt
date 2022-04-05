package com.robertconstantindinescu.data.repository

import com.robertconstantindinescu.data.local.MelanomaDao
import com.robertconstantindinescu.data.local.entity.MelanomaRecordEntity
import com.robertconstantindinescu.data.meppers.toMelanomaRecord
import com.robertconstantindinescu.data.meppers.toMelanomaRecordEntity
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.domain.repository.MelanomaRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MelanomaRepositoryImpl(
    private val dao: MelanomaDao
): MelanomaRepositoryInterface {
    override fun getMelanomaRecords(): Flow<List<MelanomaRecord>> {

        return dao.getMelanomaRecords()
            .map { melanomaRecordList ->
                melanomaRecordList.map {
                    it.toMelanomaRecord()
                }
            }
    }

    override suspend fun insertMelanomaRecord(melanomaRecord: MelanomaRecord) {

        dao.insertMelanomaRecord(melanomaRecord.toMelanomaRecordEntity())
    }

    override suspend fun deleteMelanomaRecord(melanomaRecord: MelanomaRecord) {
        dao.deleteMelanomaRecord(melanomaRecord.toMelanomaRecordEntity())
    }

    override suspend fun deleteAllMelanomaRecords() {

        dao.deleteAllMelanomaRecords()
    }
}