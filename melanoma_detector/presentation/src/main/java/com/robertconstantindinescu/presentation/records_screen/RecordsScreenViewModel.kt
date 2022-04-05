package com.robertconstantindinescu.presentation.records_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robertconstantindinescu.core.SingleUiEvent
import com.robertconstantindinescu.core.UiText
import com.robertconstantindinescu.domain.model.MelanomaRecord
import com.robertconstantindinescu.domain.use_case.global.MelanomaDetectionUseCases
import com.robertconstantindinescu.presentation.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsScreenViewModel @Inject constructor(
    private val useCases: MelanomaDetectionUseCases
) : ViewModel() {

    private val _singleUiEvent = Channel<SingleUiEvent>()
    val singleUiEvent = _singleUiEvent.receiveAsFlow()

    private val _state = MutableStateFlow<RecordsScreenState>(RecordsScreenState())
    val state: StateFlow<RecordsScreenState> = _state

//    private val _melanomaRecordsList = MutableStateFlow<List<MelanomaRecord>>(emptyList())
//    val melanomaRecord: StateFlow<List<MelanomaRecord>> = _melanomaRecordsList

    private var getMelanomaRecordsJob: Job? = null

    init {
        refreshMelanomaRecords()
    }

    private fun refreshMelanomaRecords() {
        viewModelScope.launch {
            getMelanomaRecordsJob?.cancel()
            getMelanomaRecordsJob = useCases.getMelanomaRecords
                .invoke().onEach { melanomaRecords ->
                    _state.value = state.value.copy(
                        melanomaRecordsList = melanomaRecords
                    )
                }
                .launchIn(viewModelScope)
        }

    }


    fun onEvent(event: RecordsScreenEvent) {
        when (event) {
            is RecordsScreenEvent.onDetectorActivityNavigateClick -> {
                viewModelScope.launch {
                    _singleUiEvent.send(SingleUiEvent.NavigateSuccess)
                }


            }
            is RecordsScreenEvent.onDeleteMelanomaRecord -> {

                // TODO: get the event and show snackbar in te fragment
                viewModelScope.launch {
                    useCases.deleteMelanomaRecord(event.melanomaRecord)
                    _singleUiEvent.send(SingleUiEvent.ShowSnackBar(UiText.StringResource(R.string.melanomaRecordDeleted)))
                }
            }
            is RecordsScreenEvent.onDeleteAllMelanomaRecords -> {
                viewModelScope.launch {
                    useCases.deleteAllMelanomaRecords
                    _singleUiEvent.send(SingleUiEvent.ShowSnackBar(UiText.StringResource(R.string.allMelanomaRecordDeleted)))
                }

            }
            is RecordsScreenEvent.getMelanomaRecords -> {
                viewModelScope.launch {
                    getMelanomaRecordsJob?.cancel()
                    getMelanomaRecordsJob = useCases.getMelanomaRecords
                        .invoke().onEach { melanomaRecords ->
                            _state.value = state.value.copy(
                                melanomaRecordsList = melanomaRecords
                            )
                        }
                        .launchIn(viewModelScope)
                }

            }
            is RecordsScreenEvent.onMelanoRecordClickListener -> {

            }
            is RecordsScreenEvent.onMelanomaRecordLongClickListener -> {
                val updatedSelectedMelanomaRecordList = useCases.removeFromSelectedCancerRecords(
                    event.currentRecord,
                    event.selectedCancerRecordList
                )

                _state.value = state.value.copy(
                    multiSelection = true,
                    startActionMode = true,
                    selectedMelanomaRecordsList = updatedSelectedMelanomaRecordList,

                    )


            }
        }
    }

}