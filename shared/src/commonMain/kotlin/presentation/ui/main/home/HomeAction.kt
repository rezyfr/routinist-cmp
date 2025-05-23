package presentation.ui.main.home

import presentation.component.core.ViewSingleAction

sealed class HomeAction : ViewSingleAction {
    data object Refresh : HomeAction()
}