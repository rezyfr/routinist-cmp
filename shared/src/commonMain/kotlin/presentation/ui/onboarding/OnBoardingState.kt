package presentation.ui.onboarding

import presentation.component.core.ProgressBarState
import presentation.component.core.ViewState

data class OnBoardingState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState
