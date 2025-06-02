package id.rezyfr.routinist.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HabitStatsResponse(
    @SerialName("date")
    val date: String?,
    @SerialName("success")
    val success: Int?,
    @SerialName("total")
    val total: Int?
)
