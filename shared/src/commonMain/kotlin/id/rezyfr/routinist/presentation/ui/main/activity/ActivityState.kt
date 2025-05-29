package id.rezyfr.routinist.presentation.ui.main.activity

import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.model.ActivitySummaryModel
import id.rezyfr.routinist.presentation.component.core.ViewEvent
import id.rezyfr.routinist.presentation.component.core.ViewSingleAction
import id.rezyfr.routinist.presentation.component.core.ViewState

data class ActivityState(
    val summary: UiResult<ActivitySummaryModel> = UiResult.Uninitialized
) : ViewState

sealed class ActivityEvent : ViewEvent
sealed class ActivityAction : ViewSingleAction