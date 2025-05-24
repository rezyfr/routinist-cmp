package id.rezyfr.routinist.presentation.ui.main.home

import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.domain.model.HabitSummaryModel
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.ViewState

data class HomeState(
    val summary : HabitSummaryModel = HabitSummaryModel(),
    val today: List<HabitProgressModel> = emptyList(),
    val updatingProgressId: Long = -1L,
    val calendars: List<Pair<String, String>> = emptyList(),
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState