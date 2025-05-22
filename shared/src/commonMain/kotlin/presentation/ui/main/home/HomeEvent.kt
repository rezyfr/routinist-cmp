package presentation.ui.main.home

import domain.model.HabitProgressModel
import presentation.component.core.ViewEvent

sealed class HomeEvent : ViewEvent {
    data class OnProgressFinished(val progress: HabitProgressModel) : HomeEvent()
}