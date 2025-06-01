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
        fun fromResponse(response: ActivitySummaryResponse) : ActivitySummaryModel{
            return ActivitySummaryModel(
                name = response.userHabitName.ifEmpty { "All Habits" },
                icon = response.userHabitIcon.ifEmpty { "\uD83D\uDC40" },
                successRate = response.successRate,
                completed = response.completed,
                failed = response.failed,
                pointsEarned = 0
            )
        }

        fun generateDummy(): ActivitySummaryModel {
            return ActivitySummaryModel(
                name = "All Habits",
                icon = "\uD83D\uDC40",
                successRate = 50f,
                completed = 1,
                failed = 1,
                pointsEarned = 0
            )
        }
    }
}
