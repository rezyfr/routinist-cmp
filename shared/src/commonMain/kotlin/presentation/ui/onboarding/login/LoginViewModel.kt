package presentation.ui.onboarding.login

import androidx.lifecycle.viewModelScope
import domain.handleResult
import domain.usecase.LoginUseCase
import kotlinx.coroutines.launch
import presentation.component.ProgressBarState
import presentation.component.UIComponent
import presentation.util.BaseViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase
) : BaseViewModel<LoginEvent, LoginState, LoginAction>() {
    override fun setInitialState(): LoginState {
        return LoginState()
    }

    override fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnUsernameChange -> onUsernameChange(event.value)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.value)
            is LoginEvent.Login -> login()
        }
    }

    private fun onUsernameChange(value: String) {
        setState { copy(username = value) }
    }

    private fun onPasswordChange(value: String) {
        setState { copy(password = value) }
    }

    private fun login() {
        viewModelScope.launch {
            setState { copy(progressBarState = ProgressBarState.ButtonLoading) }
            loginUseCase.execute(Pair(state.value.username, state.value.password))
                .handleResult(
                    ifError = {
                        setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                        setState { copy(progressBarState = ProgressBarState.Idle) }
                    },
                    ifSuccess = {
                        setAction { LoginAction.NavigateToMain }
                        setState { copy(progressBarState = ProgressBarState.Idle) }
                    }
                )
        }
    }
}
