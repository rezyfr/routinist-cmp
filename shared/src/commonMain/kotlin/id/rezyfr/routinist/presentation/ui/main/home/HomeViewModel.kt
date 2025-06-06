package id.rezyfr.routinist.presentation.ui.main.home

import androidx.lifecycle.viewModelScope
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.domain.usecase.CreateProgressUseCase
import id.rezyfr.routinist.domain.usecase.GetHabitSummaryUseCase
import id.rezyfr.routinist.domain.usecase.GetTodayHabitProgressesUseCase
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.component.core.UIComponent
import id.rezyfr.routinist.presentation.util.BaseViewModel
import id.rezyfr.routinist.presentation.util.getUpcomingDays
import kotlinx.coroutines.launch

class HomeViewModel(
    private val createProgressUseCase: CreateProgressUseCase,
    private val getHabitSummaryUseCase: GetHabitSummaryUseCase,
    private val getTodayHabitProgressesUseCase: GetTodayHabitProgressesUseCase
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
            is HomeEvent.Refresh -> setAction { HomeAction.Refresh }
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
                    if (it == 1L) {
                        setAction { HomeAction.NavigateToMilestone(it.toInt()) }
                    }
                    setAction { HomeAction.Refresh }
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
            getTodayHabitProgressesUseCase.execute(Unit).handleResult(
                ifError = {
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState {
                        copy(
                            today = it,
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