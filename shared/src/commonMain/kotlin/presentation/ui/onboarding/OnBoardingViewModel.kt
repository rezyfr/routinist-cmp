package presentation.ui.onboarding

import androidx.lifecycle.viewModelScope
import domain.handleResult
import domain.usecase.CheckTokenUseCase
import kotlinx.coroutines.launch
import presentation.component.core.ProgressBarState
import presentation.util.BaseViewModel

class OnBoardingViewModel(
    private val checkTokenUseCase: CheckTokenUseCase
) : BaseViewModel<OnBoardingEvent, OnBoardingState, OnBoardingAction>() {
    override fun setInitialState(): OnBoardingState {
        return OnBoardingState()
    }

    override fun onTriggerEvent(event: OnBoardingEvent) {
        // No event yet
    }

    init {
        checkToken()
    }

    fun checkToken() {
        viewModelScope.launch {
            setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
            checkTokenUseCase.execute(Unit)
                .handleResult(
                    ifError = {
                        setState { copy(progressBarState = ProgressBarState.Idle) }
                    },
                    ifSuccess = {
                        setState { copy(progressBarState = ProgressBarState.Idle) }
                        setAction { OnBoardingAction.NavigateToMain }
                    }
                )
        }
    }
}