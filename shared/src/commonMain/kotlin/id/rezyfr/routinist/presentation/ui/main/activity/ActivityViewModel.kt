package id.rezyfr.routinist.presentation.ui.main.activity

import androidx.compose.ui.input.key.Key.Companion.P
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.handleResult
import id.rezyfr.routinist.domain.usecase.GetActivitySummaryUseCase
import id.rezyfr.routinist.presentation.component.core.ProgressBarState
import id.rezyfr.routinist.presentation.util.BaseViewModel
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

class ActivityViewModel(
    private val summaryUseCase: GetActivitySummaryUseCase
) : BaseViewModel<ActivityEvent, ActivityState, ActivityAction>() {
    override fun setInitialState(): ActivityState {
        return ActivityState()
    }

    init {
        getActivitySummary()
    }

    override fun onTriggerEvent(event: ActivityEvent) {
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
}