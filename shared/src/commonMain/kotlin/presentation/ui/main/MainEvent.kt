package presentation.ui.main

import domain.model.HabitModel
import domain.model.HabitProgressModel
import presentation.component.core.ViewEvent
import presentation.ui.main.home.HomeEvent

sealed class MainEvent : ViewEvent {
    data class ShowProgressSheet(val data: HabitProgressModel) : MainEvent()
    data class OnProgressChange(val progress: Int) : MainEvent()
    data object UpdateProgress : MainEvent()

    data class OnCreateHabitChosen(val data: HabitModel) : MainEvent()
    data object ShowCreateHabitSheet : MainEvent()
}