package presentation.ui.onboarding.login

import presentation.component.core.ViewEvent

sealed class LoginEvent : ViewEvent {
    data class OnEmailChange(val value: String) : LoginEvent()
    data class OnPasswordChange(val value: String) : LoginEvent()

    data object Login : LoginEvent()
}