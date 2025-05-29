package id.rezyfr.routinist.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetActivitySummaryRequest(
    @SerialName("from")
    val from: String,
    @SerialName("to")
    val to: String,
    @SerialName("user_habit_id")
    val userHabitId: Long
)
