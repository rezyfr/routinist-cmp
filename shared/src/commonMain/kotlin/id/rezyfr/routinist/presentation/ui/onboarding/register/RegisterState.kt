package id.rezyfr.routinist.presentation.ui.onboarding.register

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.ViewState

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val gender: Gender? = null,
    val habitId: Long = -1,
    val step: Int = 1,
    val habits: UiResult<List<HabitModel>> = UiResult.Uninitialized,
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState {
    val enableButton: Boolean
        get() {
            return when (step) {
                1 -> name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && (password == confirmPassword)
                2 -> gender != null
                3 -> habitId != -1L
                else -> false
            }
        }
}