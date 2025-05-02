package data.remote.service

import data.remote.response.BaseResponse
import data.remote.response.HabitResponse
import data.remote.response.NetworkResponse
import data.util.execute
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.path
import io.ktor.http.takeFrom

class HabitServiceImpl(
    private val httpClient: HttpClient,
    val baseUrl: String
) : HabitService{

    override suspend fun getRandomHabits(): NetworkResponse<BaseResponse<List<HabitResponse>>> {
        return execute {
            httpClient.get {
                url {
                    takeFrom(baseUrl)
                    path("api/v1/habit/random")
                }
            }.body()
        }
    }
}