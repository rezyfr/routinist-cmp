package presentation.ui.main

import domain.model.HabitModel
import domain.model.HabitProgressModel
import domain.model.UnitModel
import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class MainState(
    val progressSheetState: ProgressSheetState = ProgressSheetState(),
    val createHabitSheetState: CreateHabitSheetState = CreateHabitSheetState(),
) : ViewState  {

    data class ProgressSheetState(
        val progressBarState: ProgressBarState = ProgressBarState.Idle,
        val progress: String = "",
        val data: HabitProgressModel? = null
    )

    data class CreateHabitSheetState(
        val progressBarState: ProgressBarState = ProgressBarState.Idle,
        val popularHabits: List<HabitModel> = emptyList(),
        val selectedHabit: HabitModel? = null,
        val selectedUnit: UnitModel? = null,
        val goal: String = ""
    )
}

