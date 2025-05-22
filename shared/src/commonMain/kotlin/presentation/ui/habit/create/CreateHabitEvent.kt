package presentation.ui.habit.create

import domain.model.HabitModel
import domain.model.UnitModel
import presentation.component.core.ViewEvent

sealed class CreateHabitEvent  : ViewEvent {
    data class OnInit(val habit: HabitModel) : CreateHabitEvent()
    data object OnHabitChosen : CreateHabitEvent()
}