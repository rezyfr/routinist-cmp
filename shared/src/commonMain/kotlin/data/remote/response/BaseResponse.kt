package data.remote.response

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    @SerialName("data") var data: T?,
    @SerialName("error") var error: String? = "",
    @SerialName("message") var message: String? = ""
)