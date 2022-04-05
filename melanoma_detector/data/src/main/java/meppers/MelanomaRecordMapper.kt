package com.robertconstantindinescu.data.meppers

import com.robertconstantindinescu.data.local.entity.MelanomaRecordEntity
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.domain.model.model_component.BodyLocation
import com.robertconstantindinescu.domain.model.model_component.MoleType

fun MelanomaRecordEntity.toMelanomaRecord(): MelanomaRecord{
    return MelanomaRecord(
        img = img,
        moleType = MoleType.fromString(moleType),
        bodyLocation = BodyLocation.fromString(bodyLocation),
        date = date,
        preTestResult = preTestResult
    )
}

fun MelanomaRecord.toMelanomaRecordEntity(): MelanomaRecordEntity{
    return MelanomaRecordEntity(
        img = img,
        moleType = moleType.type,
        bodyLocation = bodyLocation.location,
        date = date,
        preTestResult = preTestResult
    )
}