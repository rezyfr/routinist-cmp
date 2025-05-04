package presentation.ui.onboarding.login

import presentation.component.core.ViewSingleAction

sealed class LoginAction : ViewSingleAction {
    data object NavigateToMain : LoginAction()
}