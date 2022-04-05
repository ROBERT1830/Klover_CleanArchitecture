package com.robertconstantindinescu.domain.use_case

import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.domain.repository.MelanomaRepositoryInterface

class InsertMelanomaRecordUseCase(
    private val repository: MelanomaRepositoryInterface
) {
    suspend operator fun invoke(melanomaRecord: MelanomaRecord){
        repository.insertMelanomaRecord(melanomaRecord)
    }
}