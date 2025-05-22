package presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import domain.handleResult
import domain.model.HabitProgressModel
import domain.usecase.CreateProgressUseCase
import domain.usecase.GetHabitSummaryUseCase
import domain.usecase.GetTodayHabitsUseCase
import kotlinx.coroutines.launch
import presentation.component.core.ProgressBarState
import presentation.component.core.UIComponent
import presentation.ui.main.MainAction
import presentation.ui.main.MainEvent
import presentation.util.BaseViewModel
import presentation.util.getUpcomingDays

class HomeViewModel(
    private val createProgressUseCase: CreateProgressUseCase,
    private val getHabitSummaryUseCase: GetHabitSummaryUseCase,
    private val getTodayHabitsUseCase: GetTodayHabitsUseCase
) : BaseViewModel<HomeEvent, HomeState, HomeAction>() {

    init {
        getHabitSummary()
        getTodayHabits()
        getCalendars()
    }

    override fun setInitialState(): HomeState {
        return HomeState()
    }

    override fun onTriggerEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnProgressFinished -> finishProgress(event.progress)
        }
    }

    private fun finishProgress(data: HabitProgressModel) {
        viewModelScope.launch {
            val progress = data.goal - data.progress
            setState { copy(updatingProgressId = data.id) }
            createProgressUseCase.execute(Pair(data.id.toLong(), progress)).handleResult(
                ifError = {
                    setState { copy(updatingProgressId = -1) }
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState { copy(updatingProgressId = -1) }
                    setAction { HomeAction.CollapseProgress(data.id) }
                }
            )
        }
    }

    fun getHabitSummary() {
        viewModelScope.launch {
            setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
            getHabitSummaryUseCase.execute(Unit).handleResult(
                ifError = {
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState {
                        copy(
                            summary = it,
                            progressBarState = ProgressBarState.Idle
                        )
                    }
                }
            )
        }
    }

    fun getTodayHabits() {
        viewModelScope.launch {
            setState { copy(progressBarState = ProgressBarState.ScreenLoading) }
            getTodayHabitsUseCase.execute(Unit).handleResult(
                ifError = {
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState {
                        copy(
                            today = it.map {
                                HabitProgressModel(
                                    id = it.id.toLong(),
                                    goal = it.goal.toFloat(),
                                    icon = it.icon,
                                    unit = it.unit.name,
                                    name = it.name,
                                    progress = it.progress.toFloat(),
                                )
                            },
                            progressBarState = ProgressBarState.Idle
                        )
                    }
                }
            )
        }
    }

    private fun getCalendars() {
        setState {
            copy(calendars = getUpcomingDays(7))
        }
    }
}