package id.rezyfr.routinist.presentation.ui.main.activity

import androidx.lifecycle.viewModelScope
import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.usecase.GetActivitySummaryUseCase
import id.rezyfr.routinist.domain.usecase.GetHabitStatsUseCase
import id.rezyfr.routinist.domain.usecase.GetUserHabitsUseCase
import id.rezyfr.routinist.presentation.component.core.UIComponent
import id.rezyfr.routinist.presentation.ui.main.activity.ActivityAction.ShowUserHabits
import id.rezyfr.routinist.presentation.util.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus

class ActivityViewModel(
    private val summaryUseCase: GetActivitySummaryUseCase,
    private val getUserHabitsUseCase: GetUserHabitsUseCase,
    private val getHabitStatsUseCase: GetHabitStatsUseCase
) : BaseViewModel<ActivityEvent, ActivityState, ActivityAction>() {
    override fun setInitialState(): ActivityState {
        return ActivityState()
    }

    init {
        getActivitySummary()
        getHabitStats()
    }

    override fun onTriggerEvent(event: ActivityEvent) {
        when (event) {
            is ActivityEvent.NextWeek -> {
                setState {
                    copy(
                        now = state.value.now.plus(
                            7,
                            DateTimeUnit.DAY,
                            TimeZone.currentSystemDefault()
                        )
                    )
                }

                getActivitySummary(from = state.value.startDate, to = state.value.endDate)
                getHabitStats(from = state.value.startDate, to = state.value.endDate)
            }

            is ActivityEvent.PreviousWeek -> {
                setState {
                    copy(
                        now = state.value.now.minus(
                            7,
                            DateTimeUnit.DAY,
                            TimeZone.currentSystemDefault()
                        )
                    )
                }
                getActivitySummary(from = state.value.startDate, to = state.value.endDate)
                getHabitStats(from = state.value.startDate, to = state.value.endDate)
            }

            is ActivityEvent.OpenUserHabits -> {
                if (state.value.userHabits is UiResult.Success) {
                    setAction { ShowUserHabits(state.value.userHabits.asSuccess().orEmpty()) }
                } else {
                    getUserHabits()
                }
            }

            is ActivityEvent.OnHabitSelected -> getActivitySummary(userHabitId = event.userHabitId)
        }
    }

    private fun getActivitySummary(
        from: LocalDateTime? = null,
        to: LocalDateTime? = null,
        userHabitId: Long = 0
    ) {
        val params = GetActivitySummaryUseCase.Params(
            from = from,
            to = to,
            userHabitId = userHabitId
        )
        viewModelScope.launch {
            setState { copy(summary = UiResult.Loading) }
            summaryUseCase.execute(params).handleResult(
                ifError = {
                    setState { copy(summary = UiResult.Error(it)) }
                },
                ifSuccess = {
                    setState { copy(summary = UiResult.Success(it)) }
                }
            )
        }
    }

    private fun getUserHabits() {
        viewModelScope.launch {
            getUserHabitsUseCase.execute(Unit).handleResult(
                ifError = {
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState {
                        copy(
                            userHabits = UiResult.Success(it),
                            isUserHabitExpanded = true
                        )
                    }
                    setAction { ActivityAction.ShowUserHabits(it) }
                }
            )
        }
    }

    private fun getHabitStats(
        from: LocalDateTime? = null,
        to: LocalDateTime? = null
    ) {
        val params = GetHabitStatsUseCase.Params(
            from = from,
            to = to
        )
        viewModelScope.launch {
            setState { copy(stats = UiResult.Loading) }
            getHabitStatsUseCase.execute(params).handleResult(
                ifError = {
                    setState { copy(stats = UiResult.Error(it)) }
                    setError { UIComponent.ToastSimple(it.message.orEmpty()) }
                },
                ifSuccess = {
                    setState { copy(stats = UiResult.Success(it)) }
                }
            )
        }
    }
}