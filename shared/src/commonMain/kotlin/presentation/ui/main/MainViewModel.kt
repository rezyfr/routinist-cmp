package presentation.ui.main

import androidx.lifecycle.viewModelScope
import constants.CUSTOM_TAG
import domain.handleResult
import domain.usecase.CreateProgressUseCase
import kotlinx.coroutines.launch
import presentation.component.core.ProgressBarState
import presentation.component.core.UIComponent
import presentation.util.BaseViewModel

class MainViewModel(
    private val createProgressUseCase: CreateProgressUseCase
) : BaseViewModel<MainEvent, MainState, MainAction>() {
    override fun setInitialState(): MainState {
        return MainState()
    }

    override fun onTriggerEvent(event: MainEvent) {
        when (event) {
            is MainEvent.OnProgressChange -> {
                setState {
                    copy(
                        progressSheetState = progressSheetState.copy(
                            progress = event.progress.toString()
                        )
                    )
                }
            }

            is MainEvent.UpdateProgress -> updateProgress()
            is MainEvent.ShowProgressSheet -> {
                setState {
                    copy(
                        progressSheetState = progressSheetState.copy(
                            data = event.data,
                        )
                    )
                }
            }
        }
    }

    private fun updateProgress() {
        val progress = state.value.progressSheetState.progress.toFloat()
        val habitId = state.value.progressSheetState.data?.id ?: return
        viewModelScope.launch {
            setState { copy(progressSheetState = progressSheetState.copy(progressBarState = ProgressBarState.ButtonLoading)) }
            createProgressUseCase.execute(Pair(habitId.toLong(), progress)).handleResult(
                ifError = {
                    setState { copy(progressSheetState = progressSheetState.copy(progressBarState = ProgressBarState.Idle)) }
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setAction { MainAction.HideBottomSheet }
                    setState { copy(progressSheetState = progressSheetState.copy(progressBarState = ProgressBarState.Idle)) }
                }
            )
        }
    }
}