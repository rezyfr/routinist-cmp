package id.rezyfr.routinist.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserHabitResponse(
    @SerialName("goal")
    val goal: Int?,
    @SerialName("goal_frequency")
    val goalFrequency: String?,
    @SerialName("icon")
    val icon: String?,
    @SerialName("id")
    val id: Int?,
    @SerialName("name")
    val name: String?,
    @SerialName("progress")
    val progress: Int?,
    @SerialName("unit")
    val unit: UnitResponse?,
    @SerialName("created_at")
    val createdAt: String?
)