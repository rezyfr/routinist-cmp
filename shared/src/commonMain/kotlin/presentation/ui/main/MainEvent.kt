package presentation.ui.main

import domain.model.HabitModel
import domain.model.HabitProgressModel
import domain.model.UnitModel
import presentation.component.core.ViewEvent
import presentation.navigation.MainSheet

sealed class MainEvent : ViewEvent {
    data class ShowProgressSheet(val data: HabitProgressModel) : MainEvent()
    data class OnProgressChange(val progress: Int) : MainEvent()
    data object UpdateProgress : MainEvent()

    data class OnHabitChosen(val data: HabitModel) : MainEvent()
    data object ShowCreateHabitSheet : MainEvent()
    data class OnUnitChosen(val unit: UnitModel) : MainEvent()
    data class OnGoalChange(val goal: String) : MainEvent()
    data object OnCreateHabit : MainEvent()

    data class ResetSheetState(val sheet: MainSheet): MainEvent()
}