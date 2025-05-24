package id.rezyfr.routinist.data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateHabitRequest(
    @SerialName("unit_id") val unitId: Long,
    @SerialName("habit_id") val habitId: Long,
    @SerialName("goal") val goal: Float
)