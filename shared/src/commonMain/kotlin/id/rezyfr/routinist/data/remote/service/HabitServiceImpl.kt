package id.rezyfr.routinist.data.remote.service

import id.rezyfr.routinist.data.remote.ApiClient
import id.rezyfr.routinist.data.remote.request.CreateHabitRequest
import id.rezyfr.routinist.data.remote.request.CreateProgressRequest
import id.rezyfr.routinist.data.remote.request.GetActivitySummaryRequest
import id.rezyfr.routinist.data.remote.response.ActivitySummaryResponse
import id.rezyfr.routinist.data.remote.response.BaseResponse
import id.rezyfr.routinist.data.remote.response.CreateProgressResponse
import id.rezyfr.routinist.data.remote.response.HabitProgressResponse
import id.rezyfr.routinist.data.remote.response.HabitResponse
import id.rezyfr.routinist.data.remote.response.HabitSummaryResponse
import id.rezyfr.routinist.data.remote.response.NetworkResponse
import id.rezyfr.routinist.data.remote.response.UserHabitResponse
import id.rezyfr.routinist.data.util.execute

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

    override suspend fun getTodayHabitProgresses(): NetworkResponse<BaseResponse<List<HabitProgressResponse>>> {
        return execute {
            apiClient.get(
                endpoint = "api/v1/protected/habit/today",
            )
        }
    }

    override suspend fun getHabitSummary(): NetworkResponse<BaseResponse<HabitSummaryResponse>> {
        return execute {
            apiClient.get("/api/v1/protected/habit/progress-summary")
        }
    }

    override suspend fun postUpdateProgress(
        id: Long,
        progress: Float
    ): NetworkResponse<BaseResponse<CreateProgressResponse>> {
        return execute {
            apiClient.post(
                endpoint = "/api/v1/protected/habit/$id/progress",
                body = CreateProgressRequest(
                    progress = progress
                )
            )
        }
    }

    override suspend fun createHabit(
        habitId: Long,
        unitId: Long,
        goal: Float
    ): NetworkResponse<BaseResponse<String>> {
        return execute {
            apiClient.post(
                endpoint = "/api/v1/protected/habit/create",
                body = CreateHabitRequest(
                    habitId = habitId,
                    unitId = unitId,
                    goal = goal
                )
            )
        }
    }
    override suspend fun getActivitySummary(
        userHabitId: Long,
        startDate: String,
        endDate: String
    ): NetworkResponse<BaseResponse<ActivitySummaryResponse>> {
        return execute {
            apiClient.post(
                endpoint = "/api/v1/protected/habit/activity-summary",
                body = GetActivitySummaryRequest(
                    userHabitId = userHabitId,
                    from = startDate,
                    to = endDate
                )
            )
        }
    }

    override suspend fun getUserHabits(): NetworkResponse<BaseResponse<List<UserHabitResponse>>> {
        return execute {
            apiClient.get(
                endpoint = "api/v1/protected/habit/user-habits"
            )
        }
    }
}