package com.robertconstantindinescu.core

sealed class SingleUiEvent {

    object NavigateSuccess: SingleUiEvent()
    object NavigationUp: SingleUiEvent()
    data class ShowSnackBar(val message: UiText):SingleUiEvent()
}