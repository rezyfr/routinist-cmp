package domain.repo

import data.remote.response.HabitResponse

interface HabitRepository {
    suspend fun getRandomHabits() : Result<List<HabitResponse>>
}