package presentation.ui.main

import presentation.component.core.ViewSingleAction

sealed class MainAction : ViewSingleAction {
    data object HideBottomSheet : MainAction()
}