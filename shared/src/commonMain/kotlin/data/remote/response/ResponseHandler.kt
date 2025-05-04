package data.remote.response


sealed class NetworkResponse<out T> {
    class Success<T>(val data: BaseResponse<T>) : NetworkResponse<T>()
    class Failure(val throwable: Throwable, val code: Int? = null) : NetworkResponse<Nothing>()
}
class CustomException constructor(
    val code: Int = 0,
    override val message: String
) : Exception(message)

fun <T> NetworkResponse<BaseResponse<T>>.handleResponse() : Result<T> {
    return when (this) {
        is NetworkResponse.Success -> Result.success(data.data?.data!!)
        is NetworkResponse.Failure -> Result.failure(CustomException(code ?: 0, throwable.message ?: ""))
    }
}