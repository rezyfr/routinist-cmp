package id.rezyfr.routinist.presentation.ui.onboarding

import androidx.lifecycle.viewModelScope
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.usecase.CheckTokenUseCase
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.util.BaseViewModel
import kotlinx.coroutines.launch

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