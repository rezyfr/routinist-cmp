package id.rezyfr.routinist.data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("data") val data: T?,
    @SerialName("error") val error: String? = null,
    @SerialName("message") val message: String? = null
)