package com.robertconstantindinescu.presentation.detection_screen

import com.robertconstantindinescu.domain.model.MelanomaRecord

// TODO: DELETE
sealed class DetectorEvent{
    object OnDetectClick: DetectorEvent()
    data class OnSaveClick(val melanomaRecord: MelanomaRecord):DetectorEvent()

}

