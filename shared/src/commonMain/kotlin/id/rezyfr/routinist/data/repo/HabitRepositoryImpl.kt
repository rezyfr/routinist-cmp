package id.rezyfr.routinist.data.repo

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import id.rezyfr.routinist.RoutinistDatabase
import id.rezyfr.routinist.data.remote.response.ActivitySummaryResponse
import id.rezyfr.routinist.data.remote.response.CreateProgressResponse
import id.rezyfr.routinist.data.remote.response.HabitProgressResponse
import id.rezyfr.routinist.data.remote.response.HabitResponse
import id.rezyfr.routinist.data.remote.response.HabitSummaryResponse
import id.rezyfr.routinist.data.remote.response.UserHabitResponse
import id.rezyfr.routinist.data.remote.response.handleResponse
import id.rezyfr.routinist.data.remote.service.HabitService
import id.rezyfr.routinist.data.util.safeTransaction
import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.domain.model.UnitModel
import id.rezyfr.routinist.domain.repo.HabitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import migrations.HabitEntity

class HabitRepositoryImpl(
    private val service: HabitService,
    private val db: RoutinistDatabase
) : HabitRepository {
    override suspend fun fetchRandomHabits(): Result<List<HabitResponse>> {
        return service.getRandomHabits().handleResponse()
    }

    override suspend fun getRandomHabits(): Flow<List<HabitEntity>> {
        return db.habitQueries.getHabits().asFlow().mapToList(Dispatchers.Default)
    }

    override suspend fun savePopularHabits(habits: List<HabitModel>) {
        db.safeTransaction {
            habits.forEach {
                val units = Json.encodeToString<List<UnitModel>>(it.units)
                db.habitQueries.insertHabit(
                    id = it.id.toLong(),
                    name = it.name,
                    measurement = it.measurement,
                    icon = it.icon,
                    color = it.color,
                    units = units,
                    goal = it.defaultGoal.toLong()
                )
            }
        }
    }

    override suspend fun getTodayHabitProgresses(): Result<List<HabitProgressResponse>> {
        return service.getTodayHabitProgresses().handleResponse()
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
    override suspend fun getActivitySummary(
        userHabitId: Long,
        startDate: String,
        endDate: String
    ): Result<ActivitySummaryResponse> {
        return service.getActivitySummary(userHabitId, startDate, endDate).handleResponse()
    }

    override suspend fun getUserHabits(): Result<List<UserHabitResponse>> {
        return service.getUserHabits().handleResponse()
    }
}