package com.robertconstantindinescu.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.robertconstantindinescu.data.local.entity.MelanomaRecordEntity
import local.UserCancerDataTypeConverter

@Database(
    entities = [MelanomaRecordEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UserCancerDataTypeConverter::class)
abstract class MelanomaDatabase: RoomDatabase() {
    abstract val dao: MelanomaDao

    companion object {
        const val DATABASE_NAME = "melanoma_db"
    }
}