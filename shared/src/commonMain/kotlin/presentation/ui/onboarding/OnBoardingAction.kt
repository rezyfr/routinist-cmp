package presentation.ui.onboarding

import presentation.component.core.ViewSingleAction

sealed class OnBoardingAction : ViewSingleAction {
    data object NavigateToMain : OnBoardingAction()
}