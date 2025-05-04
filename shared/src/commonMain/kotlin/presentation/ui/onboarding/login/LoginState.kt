package presentation.ui.onboarding.login

import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState
