package id.rezyfr.routinist.presentation.ui.onboarding

import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.ViewState

data class OnBoardingState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle
) : ViewState