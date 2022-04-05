package com.robertconstantindinescu.domain.use_case

import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.domain.repository.MelanomaRepositoryInterface
import kotlinx.coroutines.flow.Flow

class GetMelanomaRecordsUseCase(
    private val repository: MelanomaRepositoryInterface
) {
    operator fun invoke(): Flow<List<MelanomaRecord>> {
        return repository.getMelanomaRecords()
    }
}