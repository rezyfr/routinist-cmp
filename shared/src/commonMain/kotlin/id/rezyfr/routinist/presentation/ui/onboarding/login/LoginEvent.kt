package id.rezyfr.routinist.presentation.ui.onboarding.login

import id.rezyfr.routinist.presentation.component.core.ViewEvent

sealed class LoginEvent : ViewEvent {
    data class OnEmailChange(val value: String) : LoginEvent()
    data class OnPasswordChange(val value: String) : LoginEvent()

    data object Login : LoginEvent()
}