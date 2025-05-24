package id.rezyfr.routinist.presentation.ui.main

import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.presentation.component.core.ViewSingleAction

sealed class MainAction : ViewSingleAction {
    data object HideBottomSheet : MainAction()
    data class ShowMilestone(val milestone: Int) : MainAction()
    data class ShowCreateHabitSheet(val list: List<HabitModel>) : MainAction()
    data class NavigateToCreateHabit(val data: HabitModel) : MainAction()
}