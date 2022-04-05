package com.robertconstantindinescu.presentation.records_screen

import androidx.fragment.app.FragmentActivity
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.presentation.records_screen.adapter.RecordsScreenAdapter

sealed class RecordsScreenEvent {
    data class onDeleteMelanomaRecord(val melanomaRecord: MelanomaRecord) : RecordsScreenEvent()
    object onDeleteAllMelanomaRecords: RecordsScreenEvent()
    object onDetectorActivityNavigateClick : RecordsScreenEvent()
    object getMelanomaRecords : RecordsScreenEvent()
    data class onMelanomaRecordLongClickListener(
        val requireActivity: FragmentActivity,
        val holder: RecordsScreenAdapter.MyViewHolder,
        val currentRecord: MelanomaRecord,
        val selectedCancerRecordList: ArrayList<MelanomaRecord>
    ) :
        RecordsScreenEvent()

    object onMelanoRecordClickListener : RecordsScreenEvent()

}
