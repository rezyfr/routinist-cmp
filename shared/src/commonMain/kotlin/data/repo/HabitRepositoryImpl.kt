package data.repo

import data.remote.response.CreateProgressResponse
import data.remote.response.HabitResponse
import data.remote.response.HabitSummaryResponse
import data.remote.response.UserHabitResponse
import data.remote.response.handleResponse
import data.remote.service.HabitService
import domain.repo.HabitRepository

class HabitRepositoryImpl(
    private val service: HabitService
) : HabitRepository {
    override suspend fun getRandomHabits(): Result<List<HabitResponse>> {
        return service.getRandomHabits().handleResponse()
    }
    override suspend fun getTodayHabits(): Result<List<UserHabitResponse>> {
        return service.getTodayHabits().handleResponse()
    }
    override suspend fun getHabitSummary(): Result<HabitSummaryResponse> {
        return service.getHabitSummary().handleResponse()
    }
    override suspend fun updateProgress(
        id: Long,
        progress: Float
    ): Result<CreateProgressResponse> {
        return service.postUpdateProgress(id, progress).handleResponse()
    }
    override suspend fun createHabit(
        habitId: Long,
        unitId: Long,
        goal: Float
    ): Result<String> {
        return service.createHabit(habitId, unitId, goal).handleResponse()
    }
}