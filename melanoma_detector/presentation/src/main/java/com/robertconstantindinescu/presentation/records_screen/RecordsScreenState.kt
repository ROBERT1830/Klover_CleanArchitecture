package com.robertconstantindinescu.presentation.records_screen

import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.core_ui.R

data class RecordsScreenState(
    val melanomaRecordsList: List<MelanomaRecord> = emptyList(),
    val selectedMelanomaRecordsList: ArrayList<MelanomaRecord> = arrayListOf(),
    val multiSelection: Boolean = false,
    val startActionMode: Boolean = false
    //val recyclerVisibility: Boolean = false
)
