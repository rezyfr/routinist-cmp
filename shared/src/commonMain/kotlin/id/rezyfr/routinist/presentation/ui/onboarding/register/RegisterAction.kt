package id.rezyfr.routinist.presentation.ui.onboarding.register

import id.rezyfr.routinist.presentation.component.core.ViewSingleAction

sealed class RegisterAction : ViewSingleAction {
    data object NavigateToMain : RegisterAction()
}