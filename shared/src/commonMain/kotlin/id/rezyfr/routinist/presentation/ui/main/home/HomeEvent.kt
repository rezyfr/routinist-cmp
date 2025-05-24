package id.rezyfr.routinist.presentation.ui.main.home

import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.presentation.component.core.ViewEvent

sealed class HomeEvent : ViewEvent {
    data object Refresh : HomeEvent()
    data class OnProgressFinished(val progress: HabitProgressModel) : HomeEvent()
}