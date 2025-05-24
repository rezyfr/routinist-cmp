package id.rezyfr.routinist.presentation.ui.onboarding.login

import id.rezyfr.routinist.presentation.component.core.ViewSingleAction

sealed class LoginAction : ViewSingleAction {
    data object NavigateToMain : LoginAction()
}