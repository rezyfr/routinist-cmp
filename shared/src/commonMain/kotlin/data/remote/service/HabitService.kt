package data.remote.service

import data.remote.response.BaseResponse
import data.remote.response.HabitResponse
import data.remote.response.HabitSummaryResponse
import data.remote.response.NetworkResponse
import data.remote.response.UserHabitResponse

interface HabitService {
    suspend fun getRandomHabits() : NetworkResponse<BaseResponse<List<HabitResponse>>>
    suspend fun getTodayHabits() : NetworkResponse<BaseResponse<List<UserHabitResponse>>>
    suspend fun getHabitSummary() : NetworkResponse<BaseResponse<HabitSummaryResponse>>
    suspend fun postUpdateProgress(id: Long, progress: Float) : NetworkResponse<BaseResponse<String>>
    suspend fun createHabit(habitId: Long, unitId: Long, goal: Float) : NetworkResponse<BaseResponse<String>>
}