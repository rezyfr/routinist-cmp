package id.rezyfr.routinist.presentation.ui.main

import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.domain.model.UnitModel
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.ViewState

data class MainState(
    val progressSheetState: ProgressSheetState = ProgressSheetState(),
    val createHabitSheetState: CreateHabitSheetState = CreateHabitSheetState(),
) : ViewState {

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

