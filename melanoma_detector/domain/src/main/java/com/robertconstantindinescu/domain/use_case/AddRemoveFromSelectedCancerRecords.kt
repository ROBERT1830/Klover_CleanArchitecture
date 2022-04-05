package com.robertconstantindinescu.domain.use_case

import com.robertconstantindinescu.domain.model.MelanomaRecord

class AddRemoveFromSelectedCancerRecords {

    operator fun invoke(
        currentRecord: MelanomaRecord,
        selectedCancerRecords: ArrayList<MelanomaRecord>
    ): ArrayList<MelanomaRecord> {
        if (selectedCancerRecords.contains(currentRecord)) {
            selectedCancerRecords.remove(currentRecord)
        }else{
            selectedCancerRecords.add(currentRecord)
        }
        return selectedCancerRecords
    }
}