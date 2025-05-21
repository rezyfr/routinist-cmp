package presentation.ui.habit.create

import presentation.component.core.ViewEvent

sealed class CreateHabitEvent  : ViewEvent {
    data object OnHabitChosen : CreateHabitEvent()
}