package data.util

import data.remote.response.BaseResponse
import data.remote.response.NetworkResponse
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess
import kotlinx.serialization.json.Json

suspend inline fun <reified T> execute(
    block: () -> HttpResponse
): NetworkResponse<T> {
    return try {
        val response = block()
        val rawBody = response.bodyAsText()

        if (response.status.isSuccess()) {
            // Try to parse as T (e.g., BaseResponse<List<HabitResponse>>)
            val body: T = Json.decodeFromString(rawBody)
            NetworkResponse.Success(BaseResponse(data = body))
        } else {
            // Try to parse error details
            val errorMessage = try {
                val errorBody = Json.decodeFromString<BaseResponse<Nothing>>(rawBody)
                errorBody.message?.ifBlank { "Unknown error" }
            } catch (e: Exception) {
                "HTTP ${response.status.value} - ${response.status.description}"
            }
            NetworkResponse.Failure(Exception(errorMessage), response.status.value)
        }
    } catch (e: Exception) {
        NetworkResponse.Failure(e)
    }
}