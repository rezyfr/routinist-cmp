package presentation.ui.onboarding.login

import presentation.component.ViewEvent

sealed class LoginEvent : ViewEvent {
    data class OnUsernameChange(val value: String) : LoginEvent()
    data class OnPasswordChange(val value: String) : LoginEvent()

    data object Login : LoginEvent()
}