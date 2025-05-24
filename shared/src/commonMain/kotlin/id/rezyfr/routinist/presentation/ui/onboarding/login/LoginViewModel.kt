package id.rezyfr.routinist.presentation.ui.onboarding.login

import androidx.lifecycle.viewModelScope
import com.russhwolf.settings.Settings
import id.rezyfr.routinist.constants.SettingsConstant
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.usecase.LoginUseCase
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.UIComponent
import kotlinx.coroutines.launch
import id.rezyfr.routinist.presentation.util.BaseViewModel

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val settings: Settings
) : BaseViewModel<LoginEvent, LoginState, LoginAction>() {

    init {
        init()
    }

    override fun setInitialState(): LoginState {
        return LoginState()
    }

    override fun onTriggerEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnEmailChange -> onEmailChange(event.value)
            is LoginEvent.OnPasswordChange -> onPasswordChange(event.value)
            is LoginEvent.Login -> login()
        }
    }

    private fun init() {
        setState { copy(email = settings.getString(SettingsConstant.EMAIL, "")) }
    }

    private fun onEmailChange(value: String) {
        setState { copy(email = value) }
    }

    private fun onPasswordChange(value: String) {
        setState { copy(password = value) }
    }

    private fun login() {
        viewModelScope.launch {
            setState { copy(progressBarState = ProgressBarState.ButtonLoading) }
            loginUseCase.execute(Pair(state.value.email, state.value.password))
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
