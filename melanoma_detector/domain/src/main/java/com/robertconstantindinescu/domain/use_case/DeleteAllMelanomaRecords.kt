package com.robertconstantindinescu.domain.use_case

import com.robertconstantindinescu.domain.repository.MelanomaRepositoryInterface

class DeleteAllMelanomaRecords(
    private val repositoryInterface: MelanomaRepositoryInterface
) {

    suspend operator fun invoke(){
        repositoryInterface.deleteAllMelanomaRecords()
    }
}