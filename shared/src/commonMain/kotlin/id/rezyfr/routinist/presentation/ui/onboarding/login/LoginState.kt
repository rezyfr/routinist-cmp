package id.rezyfr.routinist.presentation.ui.onboarding.login

import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.ViewState

data class LoginState(
    val email: String = "",
    val password: String = "",
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState
