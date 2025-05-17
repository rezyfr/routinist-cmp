package presentation.ui.main

import domain.model.HabitProgressModel
import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class MainState(
    val progressSheetState: ProgressSheetState = ProgressSheetState()
) : ViewState  {
    data class ProgressSheetState(
        val progressBarState: ProgressBarState = ProgressBarState.Idle,
        val progress: String = "",
        val data: HabitProgressModel? = null
    )
}

