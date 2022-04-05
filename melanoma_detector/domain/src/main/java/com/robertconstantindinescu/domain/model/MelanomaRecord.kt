package com.robertconstantindinescu.domain.model

import android.graphics.Bitmap
import com.robertconstantindinescu.domain.model.model_component.BodyLocation
import com.robertconstantindinescu.domain.model.model_component.MoleType

data class MelanomaRecord(
    val img: Bitmap,
    val moleType: MoleType,
    val bodyLocation: BodyLocation,
    val date: String,
    val preTestResult: String
)
