package data.repo

import data.remote.response.HabitResponse
import data.remote.response.handleResponse
import data.remote.service.HabitService
import domain.repo.HabitRepository

class HabitRepositoryImpl(
    private val service: HabitService
) : HabitRepository {
    override suspend fun getRandomHabits(): Result<List<HabitResponse>> {
        return service.getRandomHabits().handleResponse()
    }
}