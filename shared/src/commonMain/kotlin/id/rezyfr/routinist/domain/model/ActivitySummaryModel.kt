package id.rezyfr.routinist.domain.model

import id.rezyfr.routinist.data.remote.response.ActivitySummaryResponse

data class ActivitySummaryModel(
    val name: String,
    val icon: String,
    val successRate: Float,
    val completed: Long,
    val failed: Long,
    val pointsEarned: Long
) {
    companion object {
        fun fromResponse(response: ActivitySummaryResponse) = ActivitySummaryModel(
            name = response.userHabitName,
            icon = response.userHabitIcon,
            successRate = response.successRate,
            completed = response.completed,
            failed = response.failed,
            pointsEarned = 0
        )
    }
}
