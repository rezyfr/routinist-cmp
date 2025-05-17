package data.remote.service

import com.russhwolf.settings.Settings
import data.remote.ApiClient
import data.remote.request.CreateProgressRequest
import data.remote.response.BaseResponse
import data.remote.response.HabitResponse
import data.remote.response.HabitSummaryResponse
import data.remote.response.NetworkResponse
import data.remote.response.UserHabitResponse
import data.util.execute

class HabitServiceImpl(
    private val apiClient: ApiClient
) : HabitService {
    override suspend fun getRandomHabits(): NetworkResponse<BaseResponse<List<HabitResponse>>> {
        return execute {
            apiClient.get(
                endpoint = "api/v1/habit/random",
                auth = false
            )
        }
    }

    override suspend fun getTodayHabits(): NetworkResponse<BaseResponse<List<UserHabitResponse>>> {
        return execute {
            apiClient.get(
                endpoint = "api/v1/protected/habit/today",
            )
        }
    }

    override suspend fun getHabitSummary(): NetworkResponse<BaseResponse<HabitSummaryResponse>> {
        return execute {
            apiClient.get("/api/v1/protected/habit/summary")
        }
    }

    override suspend fun postUpdateProgress(
        id: Long,
        progress: Float
    ): NetworkResponse<BaseResponse<String>> {
        return execute {
            apiClient.post(
                endpoint = "/api/v1/protected/habit/$id/progress",
                body = CreateProgressRequest(
                    progress = progress
                )
            )
        }
    }
}