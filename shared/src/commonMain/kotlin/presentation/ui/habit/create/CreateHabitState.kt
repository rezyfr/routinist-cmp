package presentation.ui.habit.create

import domain.model.HabitModel
import domain.model.HabitProgressModel
import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class CreateHabitState(
    val popularHabits: List<HabitModel> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState
