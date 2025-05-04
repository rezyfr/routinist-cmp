package data.remote.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitResponse(
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("default_goal")
    val defaultGoal: Int?,
    @SerialName("id")
    val id: Int?,
    @SerialName("icon")
    val icon: String?,
    @SerialName("measurement")
    val measurement: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("units")
    val units: List<UnitResponse>,
    @SerialName("updated_at")
    val updatedAt: String?
)