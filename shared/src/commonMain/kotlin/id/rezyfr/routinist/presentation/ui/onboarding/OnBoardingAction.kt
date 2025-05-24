package id.rezyfr.routinist.presentation.ui.onboarding

import id.rezyfr.routinist.presentation.component.core.ViewSingleAction

sealed class OnBoardingAction : ViewSingleAction {
    data object NavigateToMain : OnBoardingAction()
}