package data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitSummaryResponse(
    @SerialName("completed_habit") val completedHabits: Int,
    @SerialName("total_habit") val totalHabits: Int,
    @SerialName("percentage") val percentage: Float
)
