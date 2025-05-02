package presentation.ui.onboarding.register

import presentation.component.core.ViewSingleAction

sealed class RegisterAction : ViewSingleAction {
    data object NavigateToMain : RegisterAction()
}