package id.rezyfr.routinist.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ActivitySummaryResponse(
    @SerialName("success_rate")
    val successRate: Float,
    @SerialName("completed")
    val completed: Long,
    @SerialName("failed")
    val failed: Long,
    @SerialName("user_habit_id")
    val userHabitId: Long,
    @SerialName("user_habit_name")
    val userHabitName: String,
    @SerialName("user_habit_icon")
    val userHabitIcon: String
)
