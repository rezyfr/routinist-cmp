package id.rezyfr.routinist.presentation.ui.main.activity

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.model.ActivitySummaryModel
import id.rezyfr.routinist.domain.model.UserHabitModel
import id.rezyfr.routinist.presentation.component.core.ViewEvent
import id.rezyfr.routinist.presentation.component.core.ViewSingleAction
import id.rezyfr.routinist.presentation.component.core.ViewState
import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime

data class ActivityState(
    val summary: UiResult<ActivitySummaryModel> = UiResult.Uninitialized,
    val userHabits: UiResult<List<UserHabitModel>> = UiResult.Uninitialized,
    val now: Instant = Clock.System.now(),
    val isUserHabitExpanded: Boolean = false
) : ViewState {
    val startDate: LocalDateTime
        get() = now.minus(7, DateTimeUnit.DAY, TimeZone.currentSystemDefault())
            .toLocalDateTime(TimeZone.UTC)
    val endDate: LocalDateTime get() = now.toLocalDateTime(TimeZone.currentSystemDefault())
    val formattedDate: String
        get() = "${
            startDate.month.name.lowercase().replaceFirstChar { it.uppercase() }
        } ${startDate.dayOfMonth} - ${
            endDate.month.name.lowercase().replaceFirstChar { it.uppercase() }
        } ${endDate.dayOfMonth}"
}

sealed class ActivityEvent : ViewEvent {
    data object PreviousWeek : ActivityEvent()
    data object NextWeek : ActivityEvent()
    data object OpenUserHabits : ActivityEvent()
    data class OnHabitSelected(val userHabitId: Long) : ActivityEvent()
}

sealed class ActivityAction : ViewSingleAction {
    data class ShowUserHabits(val habits: List<UserHabitModel>) : ActivityAction()
}