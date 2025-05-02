package data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UnitResponse(
    @SerialName("id")
    val id: Int?,
    @SerialName("measurement")
    val measurement: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("symbol")
    val symbol: String?
)