package id.rezyfr.routinist.domain.model

import id.rezyfr.routinist.data.remote.response.HabitSummaryResponse

data class HabitSummaryModel(
    val completedHabits: Int = 1,
    val totalHabits: Int = 1,
    val percentage: Float = 0f
) {
    val isAlmostDone get() = percentage > 50f
    companion object {
        fun fromResponse(response: HabitSummaryResponse): HabitSummaryModel {
            return HabitSummaryModel(
                completedHabits = response.completedHabits,
                totalHabits = response.totalHabits,
                percentage = response.percentage
            )
        }
    }
}
