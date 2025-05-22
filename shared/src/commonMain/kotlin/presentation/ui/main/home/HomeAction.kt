package presentation.ui.main.home

import presentation.component.core.ViewSingleAction

sealed class HomeAction : ViewSingleAction {
    data class CollapseProgress(val id: Long) : HomeAction()
}