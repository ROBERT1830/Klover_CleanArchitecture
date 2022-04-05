package com.robertconstantindinescu.presentation.detection_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.core.SingleUiEvent
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.domain.use_case.global.MelanomaDetectionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetectorViewModel @Inject constructor(
    private val useCases: MelanomaDetectionUseCases
): ViewModel(){
    private val _singleUiEvent = Channel<SingleUiEvent>()
    val singleUiEvent = _singleUiEvent.receiveAsFlow()

    private val _dateState = MutableStateFlow<String>("")
    val dateState: StateFlow<String> = _dateState

    fun insertMelanomaRecord(melanomaRecord: MelanomaRecord){
        viewModelScope.launch {

            useCases.insertMelanomaRecord(melanomaRecord)
            _singleUiEvent.send(SingleUiEvent.NavigationUp)
        }

    }

    fun generateDate() {
        _dateState.value = useCases.generateDate.invoke()

    }
}