package presentation.ui.onboarding.register

import presentation.component.core.ViewEvent

sealed class RegisterEvent : ViewEvent {
    data class OnEmailChange(val value: String) : RegisterEvent()
    data class OnPasswordChange(val value: String) : RegisterEvent()
    data class OnConfirmPasswordChange(val value: String) : RegisterEvent()
    data class OnNameChange(val value: String) : RegisterEvent()

    data class OnGenderChange(val gender: Gender) : RegisterEvent()

    data class OnHabitChange(val habitId: Int) : RegisterEvent()

    data object Next : RegisterEvent()
    data object Previous : RegisterEvent()
}