package presentation.ui.habit.create

import domain.model.HabitModel
import domain.model.UnitModel
import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class CreateHabitState(
    val habit: HabitModel? = null,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val selectedUnit: UnitModel? = null,
    val goal: Float = 0f
) : ViewState
