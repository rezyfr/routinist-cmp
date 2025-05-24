package id.rezyfr.routinist.presentation.ui.create

import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.presentation.component.core.ViewEvent

sealed class CreateHabitEvent  : ViewEvent {
    data class OnInit(val habit: HabitModel) : CreateHabitEvent()
    data object OnHabitChosen : CreateHabitEvent()
}