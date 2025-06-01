package id.rezyfr.routinist.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitResponse(
    @SerialName("created_at")
    val createdAt: String? = null,
    @SerialName("default_goal")
    val defaultGoal: Int?,
    @SerialName("id")
    val id: Long?,
    @SerialName("icon")
    val icon: String?,
    @SerialName("measurement")
    val measurement: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("units")
    val units: List<UnitResponse>,
    @SerialName("updated_at")
    val updatedAt: String? = null,
    @SerialName("color")
    val color: Long?
)