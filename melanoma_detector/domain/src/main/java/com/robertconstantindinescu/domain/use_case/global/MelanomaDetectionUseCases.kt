package com.robertconstantindinescu.domain.use_case.global

import com.robertconstantindinescu.domain.use_case.*

data class MelanomaDetectionUseCases(
    val deleteMelanomaRecord: DeleteMelanomaRecordUseCase,
    val getMelanomaRecords: GetMelanomaRecordsUseCase,
    val insertMelanomaRecord: InsertMelanomaRecordUseCase,
    val generateDate: GenerateDate,
    val removeFromSelectedCancerRecords: AddRemoveFromSelectedCancerRecords,
    val deleteAllMelanomaRecords: DeleteAllMelanomaRecords
)
