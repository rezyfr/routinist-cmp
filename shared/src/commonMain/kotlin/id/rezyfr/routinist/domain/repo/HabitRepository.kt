package id.rezyfr.routinist.domain.repo

import id.rezyfr.routinist.data.remote.response.ActivitySummaryResponse
import id.rezyfr.routinist.data.remote.response.BaseResponse
import id.rezyfr.routinist.data.remote.response.CreateProgressResponse
import id.rezyfr.routinist.data.remote.response.HabitResponse
import id.rezyfr.routinist.data.remote.response.HabitSummaryResponse
import id.rezyfr.routinist.data.remote.response.NetworkResponse
import id.rezyfr.routinist.data.remote.response.UserHabitResponse
import id.rezyfr.routinist.domain.model.HabitModel
import kotlinx.coroutines.flow.Flow
import migrations.HabitEntity

interface HabitRepository {
    suspend fun fetchRandomHabits() : Result<List<HabitResponse>>
    suspend fun getRandomHabits() : Flow<List<HabitEntity>>
    suspend fun savePopularHabits(habits: List<HabitModel>)
    suspend fun getTodayHabits() : Result<List<UserHabitResponse>>
    suspend fun getHabitSummary() : Result<HabitSummaryResponse>
    suspend fun updateProgress(id: Long, progress: Float) : Result<CreateProgressResponse>
    suspend fun createHabit(habitId: Long, unitId: Long, goal: Float) : Result<String>
    suspend fun getActivitySummary(userHabitId: Long, startDate: String, endDate: String) : Result<ActivitySummaryResponse>
}