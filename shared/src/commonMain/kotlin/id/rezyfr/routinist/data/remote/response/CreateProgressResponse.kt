package id.rezyfr.routinist.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateProgressResponse(
    @SerialName("milestone") val milestone: Long? = null
)
