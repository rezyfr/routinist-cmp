package presentation.ui.onboarding.register

import constants.CUSTOM_TAG
import domain.UiResult
import domain.model.HabitModel
import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class RegisterState(
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val name: String = "",
    val gender: Gender? = null,
    val habitId: Int = -1,
    val step: Int = 1,
    val habits: UiResult<List<HabitModel>> = UiResult.Uninitialized,
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState {
    val enableButton: Boolean
        get() {
            return when (step) {
                1 -> name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && (password == confirmPassword)
                2 -> gender != null
                3 -> habitId != -1
                else -> false
            }
        }
}