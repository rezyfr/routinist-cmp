package id.rezyfr.routinist.data.remote.service

import id.rezyfr.routinist.data.remote.response.BaseResponse
import id.rezyfr.routinist.data.remote.response.CreateProgressResponse
import id.rezyfr.routinist.data.remote.response.HabitResponse
import id.rezyfr.routinist.data.remote.response.HabitSummaryResponse
import id.rezyfr.routinist.data.remote.response.NetworkResponse
import id.rezyfr.routinist.data.remote.response.UserHabitResponse

interface HabitService {
    suspend fun getRandomHabits() : NetworkResponse<BaseResponse<List<HabitResponse>>>
    suspend fun getTodayHabits() : NetworkResponse<BaseResponse<List<UserHabitResponse>>>
    suspend fun getHabitSummary() : NetworkResponse<BaseResponse<HabitSummaryResponse>>
    suspend fun postUpdateProgress(id: Long, progress: Float) : NetworkResponse<BaseResponse<CreateProgressResponse>>
    suspend fun createHabit(habitId: Long, unitId: Long, goal: Float) : NetworkResponse<BaseResponse<String>>
}