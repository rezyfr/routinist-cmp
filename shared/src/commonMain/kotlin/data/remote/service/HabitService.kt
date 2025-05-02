package data.remote.service

import data.remote.response.BaseResponse
import data.remote.response.HabitResponse
import data.remote.response.NetworkResponse

interface HabitService {
    suspend fun getRandomHabits() : NetworkResponse<BaseResponse<List<HabitResponse>>>
}