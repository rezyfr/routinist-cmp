package id.rezyfr.routinist.presentation.ui.main

import androidx.lifecycle.viewModelScope
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.usecase.CreateHabitUseCase
import id.rezyfr.routinist.domain.usecase.CreateProgressUseCase
import id.rezyfr.routinist.domain.usecase.GetRandomHabitUseCase
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.UIComponent
import kotlinx.coroutines.launch
import id.rezyfr.routinist.presentation.navigation.MainSheet
import id.rezyfr.routinist.presentation.util.BaseViewModel

class MainViewModel(
    private val createProgressUseCase: CreateProgressUseCase,
    private val getRandomHabitUseCase: GetRandomHabitUseCase,
    private val createHabitUseCase: CreateHabitUseCase
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

            is MainEvent.OnHabitChosen -> {
                setState {
                    copy(
                        createHabitSheetState = createHabitSheetState.copy(
                            selectedHabit = event.data,
                            selectedUnit = event.data.units.first(),
                            goal = event.data.defaultGoal.toString()
                        )
                    )
                }
            }

            is MainEvent.ShowCreateHabitSheet -> {
                getPopularHabits()
            }

            is MainEvent.OnGoalChange -> {
                setState {
                    copy(
                        createHabitSheetState = createHabitSheetState.copy(
                            goal = event.goal
                        )
                    )
                }
            }

            is MainEvent.OnUnitChosen -> {
                setState {
                    copy(createHabitSheetState = createHabitSheetState.copy(selectedUnit = event.unit))
                }
            }

            is MainEvent.OnCreateHabit -> {
                createHabit()
            }

            is MainEvent.ResetSheetState -> {
                when (event.sheet) {
                    MainSheet.CreateHabit -> {
                        setState {
                            copy(
                                createHabitSheetState = createHabitSheetState.copy(
                                    selectedHabit = null,
                                    selectedUnit = null,
                                    goal = ""
                                )
                            )
                        }
                    }

                    MainSheet.CreateProgress -> {
                        setState {
                            copy(
                                progressSheetState = progressSheetState.copy(
                                    data = null,
                                    progress = "",
                                    progressBarState = ProgressBarState.Idle
                                )
                            )
                        }
                    }
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

                    if (it != null) {
                        setAction { MainAction.ShowMilestone(it.toInt()) }
                    }
                }
            )
        }
    }

    private fun getPopularHabits() {
        viewModelScope.launch {
            getRandomHabitUseCase.executeFlow(Unit).collect {
                it.handleResult(
                    ifError = {
                        setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                    },
                    ifSuccess = {
                        setState {
                            copy(
                                createHabitSheetState = createHabitSheetState.copy(
                                    progressBarState = ProgressBarState.Idle,
                                    popularHabits = it
                                )
                            )
                        }
                        setAction { MainAction.ShowCreateHabitSheet(it) }
                    }
                )
            }
        }
    }

    private fun createHabit() {
        viewModelScope.launch {
            setState {
                copy(createHabitSheetState = createHabitSheetState.copy(progressBarState = ProgressBarState.ButtonLoading))
            }
            val request = state.value.createHabitSheetState
            if (request.selectedHabit == null || request.selectedUnit == null) return@launch
            createHabitUseCase.execute(
                CreateHabitUseCase.Params(
                    habitId = request.selectedHabit.id.toLong(),
                    unitId = request.selectedUnit.id.toLong(),
                    goal = request.goal.toFloat()
                )
            ).handleResult(
                ifError = {
                    setState {
                        copy(createHabitSheetState = createHabitSheetState.copy(progressBarState = ProgressBarState.Idle))
                    }
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState {
                        copy(createHabitSheetState = createHabitSheetState.copy(progressBarState = ProgressBarState.Idle))
                    }
                    setAction { MainAction.HideBottomSheet }
                }
            )
        }
    }
}