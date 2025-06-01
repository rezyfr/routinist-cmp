package id.rezyfr.routinist.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitProgressResponse(
    @SerialName("goal")
    val goal: Long?,
    @SerialName("goal_frequency")
    val goalFrequency: String?,
    @SerialName("icon")
    val icon: String?,
    @SerialName("id")
    val id: Long?,
    @SerialName("name")
    val name: String?,
    @SerialName("progress")
    val progress: Float?,
    @SerialName("unit")
    val unit: UnitResponse?,
    @SerialName("created_at")
    val createdAt: String?
)