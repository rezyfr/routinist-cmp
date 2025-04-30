package data.util

import data.remote.response.BaseResponse
import data.remote.response.NetworkResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> execute(
    block: () -> HttpResponse
): NetworkResponse<T> {
    val result = block()

    return try {
        if (result.status.isSuccess()) {
            NetworkResponse.Success((result.body()) ?: throw Exception("Data is null"))
        } else {
            NetworkResponse.Failure(Exception((result.body() as BaseResponse<Nothing>).message), result.status.value)
        }
    } catch (e: Exception) {
        NetworkResponse.Failure(e)
    }
}
