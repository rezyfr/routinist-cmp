package id.rezyfr.routinist.presentation.ui.create

import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.domain.model.UnitModel
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.ViewState

data class CreateHabitState(
    val habit: HabitModel? = null,
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val selectedUnit: UnitModel? = null,
    val goal: Float = 0f
) : ViewState
