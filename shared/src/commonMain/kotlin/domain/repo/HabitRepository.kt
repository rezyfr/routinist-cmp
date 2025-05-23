package domain.repo

import data.remote.response.CreateProgressResponse
import data.remote.response.HabitResponse
import data.remote.response.HabitSummaryResponse
import data.remote.response.UserHabitResponse

interface HabitRepository {
    suspend fun getRandomHabits() : Result<List<HabitResponse>>
    suspend fun getTodayHabits() : Result<List<UserHabitResponse>>
    suspend fun getHabitSummary() : Result<HabitSummaryResponse>
    suspend fun updateProgress(id: Long, progress: Float) : Result<CreateProgressResponse>
    suspend fun createHabit(habitId: Long, unitId: Long, goal: Float) : Result<String>
}