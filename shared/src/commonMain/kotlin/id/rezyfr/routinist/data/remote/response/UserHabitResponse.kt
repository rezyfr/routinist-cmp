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
    val id: Long?,
    @SerialName("name")
    val name: String?,
    @SerialName("unit")
    val unit: UnitResponse?
)