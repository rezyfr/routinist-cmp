package presentation.ui.main

import androidx.lifecycle.viewModelScope
import domain.handleResult
import domain.usecase.CreateProgressUseCase
import domain.usecase.GetRandomHabitUseCase
import kotlinx.coroutines.launch
import presentation.component.core.ProgressBarState
import presentation.component.core.UIComponent
import presentation.ui.main.MainAction.NavigateToCreateHabit
import presentation.ui.main.MainAction.ShowCreateHabitSheet
import presentation.util.BaseViewModel

class MainViewModel(
    private val createProgressUseCase: CreateProgressUseCase,
    private val getRandomHabitUseCase: GetRandomHabitUseCase
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

            is MainEvent.OnCreateHabitChosen -> {
                setAction { NavigateToCreateHabit(event.data) }
            }

            is MainEvent.ShowCreateHabitSheet -> {
                getPopularHabits()
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

    private fun getPopularHabits() {
        viewModelScope.launch {
            viewModelScope.launch {
                setState {
                    copy(
                        createHabitSheetState = createHabitSheetState.copy(
                            progressBarState = ProgressBarState.ScreenLoading
                        )
                    )
                }
                getRandomHabitUseCase.execute(Unit).handleResult(
                    ifError = {
                        setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                    },
                    ifSuccess = {
                        setAction { ShowCreateHabitSheet(it) }
                    }
                )
            }
        }
    }
}