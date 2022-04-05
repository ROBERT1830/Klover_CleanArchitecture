package com.robertconstantindinescu.data.local

import androidx.room.*
import com.robertconstantindinescu.data.local.entity.MelanomaRecordEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface MelanomaDao {
    @Query("SELECT * FROM melanoma_record_entity")
    fun getMelanomaRecords(): Flow<List<MelanomaRecordEntity>>

    @Query("DELETE FROM melanoma_record_entity")
    suspend fun deleteAllMelanomaRecords()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMelanomaRecord(melanomaRecord: MelanomaRecordEntity)

    @Delete
    suspend fun deleteMelanomaRecord(melanomaRecord: MelanomaRecordEntity)


}