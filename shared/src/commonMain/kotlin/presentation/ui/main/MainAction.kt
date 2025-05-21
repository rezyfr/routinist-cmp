package presentation.ui.main

import domain.model.HabitModel
import presentation.component.core.ViewSingleAction

sealed class MainAction : ViewSingleAction {
    data object HideBottomSheet : MainAction()
    data class ShowCreateHabitSheet(val list: List<HabitModel>) : MainAction()
    data class NavigateToCreateHabit(val data: HabitModel) : MainAction()
}