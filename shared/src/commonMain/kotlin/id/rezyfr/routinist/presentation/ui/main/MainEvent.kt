package id.rezyfr.routinist.presentation.ui.main

import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.domain.model.UnitModel
import id.rezyfr.routinist.presentation.component.core.ViewEvent
import id.rezyfr.routinist.presentation.navigation.MainSheet

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