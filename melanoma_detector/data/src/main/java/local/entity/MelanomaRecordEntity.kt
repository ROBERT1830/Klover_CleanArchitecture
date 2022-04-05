package com.robertconstantindinescu.data.local.entity

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import java.io.File

@Entity(tableName = "melanoma_record_entity")
data class MelanomaRecordEntity(

    val img: Bitmap,
    val moleType: String,
    val bodyLocation: String,
    val date: String,
    val preTestResult: String,
    @PrimaryKey val recordId: Int? = null

)
