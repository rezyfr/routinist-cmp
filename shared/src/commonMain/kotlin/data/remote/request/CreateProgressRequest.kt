package data.remote.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateProgressRequest(
    @SerialName("progress") val progress: Float
)
