package id.rezyfr.routinist.presentation.ui.onboarding.register

import androidx.lifecycle.viewModelScope
import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.usecase.GetRandomHabitUseCase
import id.rezyfr.routinist.domain.usecase.RegisterUseCase
import kotlinx.coroutines.launch
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.UIComponent
import id.rezyfr.routinist.presentation.util.BaseViewModel

class RegisterViewModel(
    private val getRandomHabitUseCase: GetRandomHabitUseCase,
    private val registerUseCase: RegisterUseCase
) : BaseViewModel<RegisterEvent, RegisterState, RegisterAction>() {
    override fun setInitialState(): RegisterState {
        return RegisterState()
    }

    override fun onTriggerEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.Next -> next()
            is RegisterEvent.Previous -> previous()
            is RegisterEvent.OnEmailChange -> onEmailChange(event.value)
            is RegisterEvent.OnNameChange -> onNameChange(event.value)
            is RegisterEvent.OnPasswordChange -> onPasswordChange(event.value)
            is RegisterEvent.OnConfirmPasswordChange -> onConfirmPasswordChange(event.value)
            is RegisterEvent.OnGenderChange -> onGenderChange(event.gender)
            is RegisterEvent.OnHabitChange -> onHabitChange(event.habitId)
        }
    }

    init {
        getRandomHabits()
    }

    fun getRandomHabits() {
        viewModelScope.launch {
            setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
            getRandomHabitUseCase.executeFlow(Unit).collect {
                it.handleResult(
                    ifError = {
                        setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                    },
                    ifSuccess = {
                        setState { copy(habits = UiResult.Success(it)) }
                    }
                )
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            setState { copy(progressBarState = ProgressBarState.ButtonLoading) }
            registerUseCase.execute(
                RegisterUseCase.Params(
                    name = state.value.name,
                    email = state.value.email,
                    password = state.value.password,
                    gender = state.value.gender ?: return@launch,
                    habitId = state.value.habitId
                )
            ).handleResult(
                ifError = {
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState { copy(progressBarState = ProgressBarState.Idle) }
                }
            )
        }
    }

    fun next() {
        if (state.value.step < 3) {
            setState { copy(step = step + 1) }
        } else {
            register()
        }
    }

    fun previous() {
        if (state.value.step > 0) {
            setState { copy(step = step - 1) }
        } else {
            // Back to Login
        }
    }

    private fun onEmailChange(value: String) {
        setState { copy(email = value) }
    }

    private fun onNameChange(value: String) {
        setState { copy(name = value) }
    }

    private fun onPasswordChange(value: String) {
        setState { copy(password = value) }
    }

    private fun onConfirmPasswordChange(value: String) {
        setState { copy(confirmPassword = value) }
    }

    private fun onGenderChange(value: Gender) {
        setState { copy(gender = value) }
    }

    private fun onHabitChange(value: Int) {
        setState {
            copy(
                habitId = value
            )
        }
    }
}