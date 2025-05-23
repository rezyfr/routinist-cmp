package presentation.ui.main.home

import domain.model.HabitProgressModel
import domain.model.HabitSummaryModel
import domain.model.UserHabitModel
import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class HomeState(
    val summary : HabitSummaryModel = HabitSummaryModel(),
    val today: List<HabitProgressModel> = emptyList(),
    val updatingProgressId: Long = -1L,
    val calendars: List<Pair<String, String>> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState