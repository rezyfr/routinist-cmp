package id.rezyfr.routinist.presentation.ui.main.home

import id.rezyfr.routinist.presentation.component.core.ViewSingleAction

sealed class HomeAction : ViewSingleAction {
    data object Refresh : HomeAction()
    data class NavigateToMilestone(val milestone: Int) : HomeAction()
}